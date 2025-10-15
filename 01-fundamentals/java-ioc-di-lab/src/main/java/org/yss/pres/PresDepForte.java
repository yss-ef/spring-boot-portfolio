package org.yss.pres;

import org.yss.dao.IDao;
import org.yss.dao.DaoImpl;
import org.yss.metier.IMetier;
import org.yss.metier.MetierImpl;

public class PresDepForte {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl(dao);

        System.out.println(metier.calculate());
    }
}
