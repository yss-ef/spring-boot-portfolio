package com.youssef.backend.web;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.exeptions.CustomerNotFoundException;
import com.youssef.backend.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des clients.
 * Expose les endpoints pour créer, lire, mettre à jour et supprimer des clients.
 */
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/customers")
public class CustomerRestController {
    private CustomerService customerService;

    /**
     * Récupère tous les clients.
     * @return Liste des clients
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.listCustomers();
    }

    /**
     * Récupère les clients avec le keyword.
     * @param keyword Le mot-clé de recherche
     * @return Liste des clients
     */
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return customerService.searchCustomers(keyword);
    }


    /**
     * Récupère un client par son ID.
     * @param id L'ID du client
     * @return Le client trouvé
     * @throws CustomerNotFoundException Si le client n'existe pas
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return customerService.findCustomerById(id);
    }

    /**
     * Crée un nouveau client.
     * @param request Les données du client
     * @return Le client créé
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
        return customerService.saveCustomer(request);
    }

    /**
     * Met à jour partiellement un client.
     * @param customerDTO Les nouvelles données
     * @param id L'ID du client à mettre à jour
     * @return Le client mis à jour
     * @throws CustomerNotFoundException Si le client n'existe pas
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return customerService.patchCustomer(customerDTO, id);
    }

    /**
     * Supprime un client.
     * @param id L'ID du client à supprimer
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable(name = "id")Long id){
        customerService.delCustomerById(id);
    }
}
