package org.yss.pres;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yss.metier.IMetier;

public class PresSpringXml {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("config.xml");
        IMetier metier= (IMetier) context.getBean("metier");
        System.out.println(metier.calculate());
    }
}
