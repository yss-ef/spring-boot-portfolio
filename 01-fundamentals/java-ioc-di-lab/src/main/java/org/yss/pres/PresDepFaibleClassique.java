package org.yss.pres;

import org.yss.dao.IDao;
import org.yss.metier.IMetier;

import java.io.File;
import java.util.Scanner;

public class PresDepFaibleClassique {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("config.txt"));

        //Creer un objet de type IDao

        //1.Lire le fichier de config
        String daoClassName = sc.nextLine();
        //2. Allouer la memoire
        Class cDao = Class.forName(daoClassName);
        //3. Instancier l'objet
        IDao dao = (IDao) cDao.newInstance();

        //Creer un objet de type IMetier

        //1.Lire le fichier de config
        String metierClassName = sc.nextLine();
        //2. Allouer la memoire
        Class cMetier = Class.forName(metierClassName);
        //3. Instancier l'objet
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        /*
        Ici le .getConstructor(IDao.class) va chercher le constructeur de la class MetierImpl
        qui prend comme paramètre un objet de type IDao
         */

        System.out.println(metier.calculate());
    }
}
