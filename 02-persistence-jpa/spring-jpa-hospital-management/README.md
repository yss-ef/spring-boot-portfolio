# Architecture JEE - JPA, Hibernate et Spring Data : Exemple Pratique 👨‍💻

Ce projet est une application Spring Boot illustrant les concepts fondamentaux de la **Persistance de Données** avec **JPA (Java Persistence API)**, l'implémentation **Hibernate**, et la couche d'accès aux données simplifiée par **Spring Data JPA**.

Il suit la structure et les relations d'un système de gestion hospitalière, tels qu'expliqués dans le tutoriel vidéo [Use case JPA, Hibernate Spring Data, One To Many, One To One](http://www.youtube.com/watch?v=Kfv_7m8INlU) du Professeur Mohamed YOUSSFI.

---

## Technologies et Dépendances Utilisées

Ce projet est construit avec **Spring Boot** et utilise les briques technologiques suivantes (définies dans le `pom.xml` du dépôt) :

| Technologie | Description |
| :--- | :--- |
| **Spring Data JPA** | Facilite l'implémentation des couches d'accès aux données. |
| **H2 Database** | Base de données embarquée et en mémoire, parfaite pour le développement et les tests. |
| **Lombok** | Réduit la verbosité du code (gestion des *getters*, *setters*, constructeurs, etc.). |
| **Spring Web** | Permet de construire des applications web et des API REST. |
| **JPA / Hibernate** | Couche d'Object-Relational Mapping (ORM) pour la gestion des entités et des relations. |

---

## Concepts Clés Abordés

Le projet met en œuvre plusieurs concepts essentiels de la persistance et de l'architecture d'application en JEE/Spring :

### 1. Modélisation Entité (ORM)

Le modèle de données est basé sur quatre entités principales, illustrant différentes relations :

* `Patient`
* `Medecin`
* `RendezVous`
* `Consultation`

### 2. Gestion des Relations Bidirectionnelles

L'exemple se concentre sur les relations **One-to-Many** et **One-to-One** (par exemple, un `Patient` peut avoir plusieurs `RendezVous`).

* **Propriétaire de la Relation :** Dans une relation bidirectionnelle (comme `Patient` et `RendezVous`), l'annotation `mappedBy` est utilisée sur le côté non-propriétaire (`Patient`) pour indiquer que c'est le côté `RendezVous` qui contient la clé étrangère.

### 3. Couche Service (Métier)

La vidéo insiste sur l'importance de la **Couche Service** (`@Service`) pour encapsuler la **logique métier** et assurer la **gestion transactionnelle** (`@Transactional`). Cette couche fait le lien entre la couche de présentation (Web) et la couche de données (Repository).

### 4. Injection de Dépendances

L'injection des dépendances est réalisée via le **constructeur**, la méthode recommandée par Spring pour assurer l'immutabilité et la visibilité des dépendances.

---

## Annotations JPA/ORM Essentielles

| Annotation | Contexte | Rôle |
| :--- | :--- | :--- |
| **`@Entity`** | Classe | Marque une classe comme une entité JPA, correspondant à une table dans la base de données. |
| **`@Id`** | Attribut | Marque l'attribut comme étant la clé primaire de l'entité. |
| **`@GeneratedValue(...)`** | Attribut (`@Id`) | Spécifie la stratégie de génération de la clé primaire (souvent `GenerationType.IDENTITY`). |
| **`@OneToMany`** | Attribut (Collection) | Mappe une relation un à plusieurs (ex: un Patient a plusieurs RendezVous). Utilise souvent `mappedBy`. |
| **`@ManyToOne`** | Attribut (Objet) | Mappe une relation plusieurs à un (ex: un RendezVous concerne un seul Patient). Crée la clé étrangère. |
| **`@Enumerated(EnumType.STRING)`** | Attribut (Enum) | Force l'entité à stocker le nom textuel de la valeur Enum (ex: `PENDING` ou `CANCELLED`) et non son index numérique (ordinal). |
| **`@JsonIgnore`** ou **`@JsonIgnoreProperties`** | Attribut ou Classe | Utilisé pour briser la **boucle infinie** de sérialisation JSON (`Patient -> RendezVous -> Patient`) dans les API REST. |

---

## Extraits de Code Illustratifs (du Dépôt)

### 1. Exemple d'Entité : `RendezVous.java`

L'extrait montre l'utilisation de Lombok, des annotations JPA pour le mapping ORM, et la gestion des relations **plusieurs-à-un** (`@ManyToOne`).

```java
// src/main/java/.../entities/RendezVous.java

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RendezVous {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    
    // Pour persister l'Enum en tant que String
    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;

    // Relation ManyToOne: Un RendezVous concerne un seul Patient
    @ManyToOne
    private Patient patient;

    // Relation ManyToOne: Un RendezVous concerne un seul Medecin
    @ManyToOne
    private Medecin medecin;

    // Relation OneToOne: Un RendezVous a une seule Consultation
    // MappedBy dans RendezVous, car Consultation contient la clé étrangère du RendezVous
    // @JsonIgnore pour éviter la boucle de sérialisation JSON
    @OneToOne(mappedBy = "rendezVous")
    @JsonIgnore
    private Consultation consultation; 
}
```
### 2. Exemple d'Interface Repository : PatientRepository.java

Spring Data JPA permet de définir des méthodes de recherche personnalisées sans écrire de SQL (méthodes Query Derivation).

```java
// src/main/java/.../repositories/PatientRepository.java

public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Exemple de Query Derivation : trouver un patient par son nom
    Patient findByNom(String nom);
}
```

### 3. Exemple de Couche Service : HopitalServiceImpl.java

Le service utilise @Transactional pour garantir l'atomicité des opérations et injecte les Repositories nécessaires via le constructeur.

```java
// src/main/java/.../service/HopitalServiceImpl.java

@Service
@Transactional
@RequiredArgsConstructor // Génère le constructeur avec tous les arguments finaux/non-null
public class HopitalServiceImpl implements IHopitalService {
    
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;
    
    // Implémentation de l'opération d'enregistrement
    @Override
    public Patient savePatient(Patient patient) {
        // Logique métier avant l'enregistrement (ex: validation)
        return patientRepository.save(patient);
    }
    
    // ... autres méthodes ...
}
```

### 4. Initialisation des Données (Test) : Application.java

La méthode @Bean qui retourne un CommandLineRunner permet d'exécuter du code au démarrage de l'application (pour l'injection de données de test, par exemple).

```java
// src/main/java/.../Application.java

@SpringBootApplication
public class Application {
    
    // ... main method ...
    
    @Bean
    CommandLineRunner start(IHopitalService hopitalService, PatientRepository patientRepository) {
        return args -> {
            
            // 1. Enregistrement des Patients via la Couche Service
            hopitalService.savePatient(new Patient(null, "Hassan", new Date(), false, null));
            hopitalService.savePatient(new Patient(null, "Salma", new Date(), true, null));

            // 2. Consultation des Patients
            System.out.println("Liste des patients enregistrés : ");
            patientRepository.findAll().forEach(p -> {
                System.out.println("ID: " + p.getId() + ", Nom: " + p.getNom());
            });

            // ... et ainsi de suite pour les Médecins, RendezVous, etc.
        };
    }
}
```
