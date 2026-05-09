package com.youssef.backend.config;

import com.youssef.backend.bot.TelegramBotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Classe de configuration pour le bot Telegram.
 * Initialise l'API Telegram Bots et enregistre notre bot.
 */
@Configuration
public class BotConfig {

    /**
     * Bean permettant d'enregistrer le bot auprès de l'API Telegram.
     *
     * @param telegramBotService Le service de notre bot à enregistrer.
     * @return L'instance de l'API Telegram Bots configurée.
     * @throws TelegramApiException En cas d'erreur lors de l'enregistrement du bot.
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBotService telegramBotService) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramBotService);
        return api;
    }
}