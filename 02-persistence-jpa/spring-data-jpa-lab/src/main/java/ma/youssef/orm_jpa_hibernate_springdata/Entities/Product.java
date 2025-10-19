package ma.youssef.orm_jpa_hibernate_springdata.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Pour une entité
@Data @NoArgsConstructor @AllArgsConstructor
/*
    @Data : Pour generer les constructeurs.
    @NoArgsConstructor et @AllArgsConstructor : Pour les constructeurs de la classe.
*/
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Pour generer la cle primaire de la table Produit
    private Long id; //Le type doit etre Long et non long car ce dernier n'est pas nullable.
    private String name;
    private double price;
    private int quantity;
}
