# Compte Rendu du TP : Inversion de Contrôle et Injection de Dépendances

Ce document sert de rapport pour le travail pratique portant sur les principes de **l'Inversion de Contrôle (IoC)** et de **l'Injection de Dépendances (DI)** en Java. L'objectif est de mettre en œuvre une application simple en respectant le principe de **couplage faible** pour en faciliter la maintenance et l'évolutivité.

## 1. Énoncé du TP

La consigne était de reprendre l'exemple traité dans les ressources vidéo fournies, en suivant les étapes ci-dessous.

> **Consignes :**
> * Créer un repository Github.
> * Déposer le lien du repository comme seul livrable dans Classroom.
> * Pour chaque période de 30 min environ, effectuer un commit et un push.
> * Pour le rapport, utiliser le fichier README.MD du repository.
> * À la fin de la séance, faire un dernier commit.
> * Après, vous continuez à compléter l'activité pratique.

> **Partie 1 : (Voir support et vidéo)**
> 1.  Créer l'interface `IDao` avec une méthode `getData`.
> 2.  Créer une implémentation de cette interface.
> 3.  Créer l'interface `IMetier` avec une méthode `calcul`.
> 4.  Créer une implémentation de cette interface en utilisant le **couplage faible**.
> 5.  Faire l'injection des dépendances :
>     a. Par instanciation statique.
>     b. Par instanciation dynamique.
>     c. En utilisant le Framework Spring (Version XML et Version annotations).

---

## 2. Conception de l'Application

Pour répondre à l'exigence de **couplage faible**, l'application est conçue autour d'interfaces qui définissent des contrats, plutôt que des implémentations concrètes.

* **`IDao`** : Interface pour la couche d'accès aux données (Data Access Object). Elle définit un contrat pour obtenir une donnée (`double getData()`).
* **`IMetier`** : Interface pour la couche métier (logique applicative). Elle définit un contrat pour effectuer un calcul (`double calculate()`).

La classe `MetierImpl` (implémentation de `IMetier`) dépend de l'interface `IDao`, et non d'une classe comme `DaoImpl`. Cela signifie que l'on peut changer l'implémentation de la couche DAO (par exemple, passer d'une base de données à un web service) sans jamais modifier le code de la couche métier.

Cette architecture rend l'application **ouverte à l'extension mais fermée à la modification**.

---

## 3. Implémentation et Code Source

Voici les différentes étapes de l'implémentation, conformément à l'énoncé.

### Étape 1 à 4 : Création des Interfaces et Implémentations

#### Interface `IDao.java`
Définit le contrat pour l'accès aux données.
```java
package org.yss.dao;

public interface IDao {
    public double getData();
}
```
#### Implémentation `DaoImpl.java`
Une première implémentation qui simule une récupération de données depuis une base de données.
```java
package org.yss.dao;

import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Utilisation de la BDD");
        double data = 31;
        return data;
    }
}
```
#### Interface `IMetier.java`
Définit le contrat pour la logique métier.
```Java
package org.yss.metier;

public interface IMetier {
    public double calculate();
}
```

#### Implémentation `MetierImpl.java`
Implémentation de la logique métier. On remarque qu'elle dépend de IDao` (couplage faible).
```Java
package org.yss.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yss.dao.IDao;

@Component
public class MetierImpl implements IMetier {
    @Autowired
    IDao dao;

    @Override
    public double calculate() {
        double t = dao.getData();
        double result = t * 21 * Math.PI;
        return result;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```
---
### Étape 5 : Les Différentes Méthodes d'Injection de Dépendances

L'objectif est d'injecter une implémentation de `IDao` dans l'objet `MetierImpl`.

#### a. Injection par Instanciation Statique

C'est la méthode la plus simple mais la moins flexible. On instancie directement les classes dans le code. Le couplage, bien que basé sur des interfaces, reste "statique" car un changement d'implémentation de `IDao` (ex: `DaoImpl2`) nécessite de modifier le code et de le recompiler.

**Fichier `Pres.java` :**
```java
package org.yss.pres;

import org.yss.dao.IDao;
import org.yss.dao.DaoImpl;
import org.yss.metier.IMetier;
import org.yss.metier.MetierImpl;

public class Pres {
    public static void main(String[] args) {
        // Injection via le constructeur
        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl(dao);

        System.out.println(metier.calculate());
    }
}
```
Résultat d'exécution :
```
Utilisation de la BDD
2045.353833219463
``
#### b. Injection par Instanciation Dynamique
Cette méthode améliore la flexibilité. On externalise le nom des classes à instancier dans un fichier de configuration (`config.txt). L'application lit ce fichier au démarrage pour savoir quelles implémentations utiliser, grâce à la réflexivité Java.
**Fichier `config.txt :**
`
org.yss.ext.DaoImpl2
org.yss.metier.MetierImpl
`
**Fichier `Pres2.java` :**
```Java
package org.yss.pres;

import org.yss.dao.IDao;
import org.yss.metier.IMetier;
import java.io.File;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("config.txt"));

        // Instanciation dynamique de la couche DAO
        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance();

        // Instanciation dynamique de la couche Métier et injection
        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        System.out.println(metier.calculate());
    }
}
```
**Résultat d'exécution (avec `DaoImpl2`) :**
```
Utilisation d'un web service
1385.4423602330987
``
#### c. Injection avec le Framework Spring
C'est l'approche la plus robuste et la plus utilisée en industrie. On délègue la création et l'injection des objets à un conteneur IoC : Spring.
##### Version XML
La configuration des objets (les "beans") et de leurs dépendances se fait dans un fichier XML.
**Fichier ``config.xml` (dans `src/main/resources`) :**
```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans)"
       xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
       xsi:schemaLocation="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans) [http://www.springframework.org/schema/beans/spring-beans.xsd](http://www.springframework.org/schema/beans/spring-beans.xsd)">

    <bean id="d" class="org.yss.dao.DaoImpl"></bean>

    <bean id="metier" class="org.yss.metier.MetierImpl">
        <property name="dao" ref="d"></property>
    </bean>
</beans>
```
**Fichier PresSpringXml.java` :**
```Java
package org.yss.pres;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yss.metier.IMetier;

public class PresSpringXml {
    public static void main(String[] args) {
        // 1. Charger le contexte Spring à partir du fichier XML
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        // 2. Récupérer le bean "metier"
        IMetier metier = (IMetier) context.getBean("metier");
        // 3. Utiliser l'objet
        System.out.println(metier.calculate());
    }
}
```
##### Version Annotations
Cette version, plus moderne, remplace le XML par des annotations directement dans le code Java. Spring scanne les packages pour trouver et configurer les beans automatiquement.
  * `@Component : Indique que la classe est un bean géré par Spring.
  * `@Autowired` : Demande à Spring d'injecter automatiquement une dépendance.
**Fichier `PresSpringAnnotation.java :**
```Java
package org.yss.pres;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.yss.metier.IMetier;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        // 1. Démarrer le contexte Spring en scannant les packages
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.yss.dao", "org.yss.metier");
        // 2. Récupérer le bean de type IMetier
        IMetier metier = ctx.getBean(IMetier.class);
        // 3. Utiliser l'objet
        System.out.println(metier.calculate());
    }
}
```

##### Résultat d'exécution (pour les deux versions Spring) :
```
Utilisation de la BDD
2045.353833219463
```
---
## 4. Conclusion
Ce travail pratique a permis de mettre en évidence l'importance du principe de l'Inversion de Contrôle pour créer des applications modulaires et faciles à maintenir.

Nous avons vu que l'injection de dépendances est le mécanisme qui permet de mettre en œuvre l'IoC. En passant de l'instanciation statique à l'instanciation dynamique, puis au framework Spring, nous avons gagné en flexibilité et en maintenabilité.

L'utilisation d'un framework comme Spring est aujourd'hui un standard, car il automatise entièrement la gestion du cycle de vie des objets et de leurs dépendances, permettant au développeur de se concentrer sur la logique métier. La version par annotations est particulièrement appréciée pour sa simplicité et sa clarté.
