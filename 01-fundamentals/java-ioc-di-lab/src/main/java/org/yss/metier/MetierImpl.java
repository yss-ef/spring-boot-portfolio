package org.yss.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yss.dao.IDao;

@Component
public class MetierImpl implements IMetier {
    //Initialiser un objet de type IDao, sa valeur sera null
    IDao dao;

    //Constructeur sans paramètre
    public MetierImpl() {
    }

    //Constructeur avec paramètre

    @Autowired
    public MetierImpl(@Qualifier("dao_web") IDao dao) {
        /*On va utiliser @Qualifier("${dao.implementation.active}") a utiliser pour le rendre vraiment dynamyque avec le .properties*/
        this.dao = dao;
    }

    @Override
    public double calculate() {
        double t = dao.getData();
        double result = t * 21 * Math.PI;
        return result;
    }

    /*
    l'objet dao est null donc on ne peu pas l'appeler. il faut
    lui injecter une valeur, pour ça deux methode existe:
    1. constructeur avec paramètre (méthode conseillé)
    2. getters
     */

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
