package org.yss.ext;

import org.yss.dao.IDao;

public class DaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Utilisation d'un web service");
        double data = 21;
        return data;
    }
}
