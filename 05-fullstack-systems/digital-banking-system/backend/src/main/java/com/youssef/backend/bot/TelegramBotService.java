package com.youssef.backend.bot;

import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.exeptions.BalanceNotSufficientException;
import com.youssef.backend.exeptions.BankAccountNotFoundException;
import com.youssef.backend.repositories.AccountOperationRepository;
import com.youssef.backend.repositories.CustomerRepository;
import com.youssef.backend.services.AccountOperationService;
import com.youssef.backend.services.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service gérant le bot Telegram.
 * Ce service écoute les messages entrants, authentifie les utilisateurs via leur ID Telegram,
 * et traite les demandes soit via des commandes strictes (virements), soit via une IA conversationnelle.
 */
@Component
public class TelegramBotService extends TelegramLongPollingBot {

    private final OpenAiService openAiService;
    private final CustomerRepository customerRepository;
    private final AccountOperationService accountOperationService; // Ton service transactionnel
    private final AccountOperationRepository accountOperationRepository; // Pour l'historique (lecture)
    private final String botUsername;

    public TelegramBotService(@Value("${telegram.bot.token}") String botToken,
                              @Value("${telegram.bot.username}") String botUsername,
                              OpenAiService openAiService,
                              CustomerRepository customerRepository,
                              AccountOperationService accountOperationService,
                              AccountOperationRepository accountOperationRepository) {
        super(botToken);
        this.botUsername = botUsername;
        this.openAiService = openAiService;
        this.customerRepository = customerRepository;
        this.accountOperationService = accountOperationService;
        this.accountOperationRepository = accountOperationRepository;
    }

    /**
     * Méthode principale appelée à chaque réception de message.
     * @param update L'objet contenant le message et les métadonnées.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageUser = update.getMessage().getText();
            long telegramId = update.getMessage().getChatId();

            // 1. Identification du client
            // Assure-toi que ta méthode dans le repo s'appelle bien findByIdTelegram ou findByIdTelegram (selon ton nommage)
            Customer client = customerRepository.findByTelegramId(telegramId);

            if (client == null) {
                // --- ZONE NON AUTHENTIFIÉE ---
                if (messageUser.startsWith("/link")) {
                    lienCompte(messageUser, telegramId);
                } else {
                    envoyerMessage(telegramId, "👋 Bonjour ! Je ne vous connais pas.\n" +
                            "Pour lier votre compte bancaire, tapez : `/link votre@email.com`");
                }
            } else {
                // --- ZONE AUTHENTIFIÉE ---

                // Cas A : Commande de virement (Géré par CODE Java strict)
                if (messageUser.startsWith("/vir")) {
                    handleVirement(messageUser, telegramId, client);
                }
                // Cas B : Conversation (Géré par IA avec contexte)
                else {
                    handleConversationIA(messageUser, telegramId, client);
                }
            }
        }
    }

    // --- LOGIQUE MÉTIER : AUTHENTIFICATION ---

    /**
     * Lie un compte client existant à un identifiant Telegram.
     * @param message Le message contenant la commande /link et l'email.
     * @param telegramId L'identifiant Telegram de l'utilisateur.
     */
    private void lienCompte(String message, Long telegramId) {
        try {
            // Format : "/link email@test.com"
            String[] parts = message.split(" ");
            if (parts.length < 2) {
                envoyerMessage(telegramId, "❌ Format incorrect. Utilisez : `/link email@exemple.com`");
                return;
            }
            String email = parts[1].trim();

            Customer c = customerRepository.findByEmail(email);

            if (c != null) {
                c.setTelegramId(telegramId); // Adapte selon le nom exact dans ton entité (telegramId vs idTelegram)
                customerRepository.save(c);
                envoyerMessage(telegramId, "✅ Compte lié avec succès à " + c.getName() + " !\n" +
                        "Vous pouvez maintenant demander votre solde ou faire un virement.");
            } else {
                envoyerMessage(telegramId, "❌ Email introuvable dans la banque.");
            }
        } catch (Exception e) {
            envoyerMessage(telegramId, "❌ Erreur technique lors de la liaison.");
            e.printStackTrace();
        }
    }

    // --- LOGIQUE MÉTIER : VIREMENT (Strict & Transactionnel) ---

    /**
     * Traite une demande de virement bancaire.
     * @param message Le message contenant la commande /vir, les comptes et le montant.
     * @param chatId L'identifiant du chat pour la réponse.
     * @param client Le client authentifié.
     */
    private void handleVirement(String message, Long chatId, Customer client) {
        try {
            // Format : /vir {source} {dest} {montant}
            String[] parts = message.split(" ");
            if (parts.length != 4) {
                envoyerMessage(chatId, "⚠️ Format incorrect.\nUsage : `/vir [MonCompteID] [DestID] [Montant]`");
                return;
            }

            String sourceId = parts[1];
            String destId = parts[2];
            double amount = Double.parseDouble(parts[3]);

            // SÉCURITÉ : Vérifier que le compte source appartient bien à l'utilisateur
            boolean isOwner = client.getBankAccounts().stream()
                    .anyMatch(acc -> acc.getId().equals(sourceId));

            if (!isOwner) {
                envoyerMessage(chatId, "⛔ Le compte source " + sourceId + " ne vous appartient pas.");
                return;
            }

            // Appel du Service Transactionnel
            accountOperationService.transfer(sourceId, destId, amount);
            envoyerMessage(chatId, "✅ Virement de " + amount + " MAD effectué avec succès !");

        } catch (NumberFormatException e) {
            envoyerMessage(chatId, "❌ Montant invalide.");
        } catch (BalanceNotSufficientException e) {
            envoyerMessage(chatId, "❌ Solde insuffisant.");
        } catch (BankAccountNotFoundException e) {
            envoyerMessage(chatId, "❌ Compte introuvable.");
        } catch (Exception e) {
            envoyerMessage(chatId, "❌ Erreur : " + e.getMessage());
        }
    }

    // --- LOGIQUE MÉTIER : IA (Conversationnelle) ---

    /**
     * Gère une conversation avec l'IA en fournissant le contexte financier du client.
     * @param userMessage Le message de l'utilisateur.
     * @param chatId L'identifiant du chat.
     * @param client Le client authentifié.
     */
    private void handleConversationIA(String userMessage, Long chatId, Customer client) {
        // 1. Construire le contexte (Soldes + Dernières opérations)
        String contexteBancaire = construireContexteFinancier(client);

        // 2. Créer le System Prompt
        String systemPrompt = "Tu es un assistant bancaire intelligent pour le client " + client.getName() + ". " +
                "Données financières en temps réel : [" + contexteBancaire + "]. " +
                "Si l'utilisateur demande son solde, utilise les données fournies. " +
                "Si l'utilisateur veut faire un virement, explique-lui qu'il doit utiliser la commande : /vir [Source] [Dest] [Montant]. " +
                "Réponds de manière concise et professionnelle.";

        // 3. Appel OpenAI
        String fullPrompt = "Instructions système: " + systemPrompt + "\n\nQuestion client: " + userMessage;
        String reponseIA = openAiService.generateResponse(fullPrompt); // Assure-toi que cette méthode existe dans ton service IA

        envoyerMessage(chatId, reponseIA);
    }

    private String construireContexteFinancier(Customer client) {
        try {
            if (client.getBankAccounts() == null || client.getBankAccounts().isEmpty()) {
                return "Le client n'a aucun compte.";
            }

            StringBuilder sb = new StringBuilder();
            for (BankAccount acc : client.getBankAccounts()) {
                sb.append("Compte ").append(acc.getId())
                        .append(" (Type: ").append(acc.getClass().getSimpleName()).append(")")
                        .append(" : Solde = ").append(acc.getBalance()).append(" MAD. ");

                // Optionnel : Ajouter les 3 dernières opérations pour que l'IA puisse en parler
                List<AccountOperation> lastOps = accountOperationRepository.findByBankAccountIdOrderByDateDesc(acc.getId());
                if(!lastOps.isEmpty()){
                    sb.append("Dernière op: ").append(lastOps.get(0).getOperationType()).append(" ").append(lastOps.get(0).getAmount()).append("; ");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "Erreur récupération contexte données.";
        }
    }

    // --- UTILITAIRES ---
    private void envoyerMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}