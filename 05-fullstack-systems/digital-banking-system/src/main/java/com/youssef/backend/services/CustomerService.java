package com.youssef.backend.services;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.exeptions.CustomerNotFoundException;

import java.util.List;

/**
 * Interface définissant les services liés à la gestion des clients.
 */
public interface CustomerService {
    /**
     * Enregistre un nouveau client.
     * @param customerDTO Les données du client à enregistrer
     * @return Le client enregistré
     */
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    
    /**
     * Récupère la liste de tous les clients.
     * @return Liste des clients
     */
    List<CustomerDTO> listCustomers();
    
    /**
     * Trouve un client par son ID.
     * @param id L'ID du client
     * @return Le client trouvé
     * @throws CustomerNotFoundException Si le client n'existe pas
     */
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    
    /**
     * Met à jour partiellement les informations d'un client.
     * @param customerDTO Les nouvelles données
     * @param id L'ID du client à mettre à jour
     * @return Le client mis à jour
     * @throws CustomerNotFoundException Si le client n'existe pas
     */
    CustomerDTO patchCustomer(CustomerDTO customerDTO,Long id ) throws CustomerNotFoundException;
    
    /**
     * Supprime un client par son ID.
     * @param id L'ID du client à supprimer
     */
    void delCustomerById(Long id);
}
