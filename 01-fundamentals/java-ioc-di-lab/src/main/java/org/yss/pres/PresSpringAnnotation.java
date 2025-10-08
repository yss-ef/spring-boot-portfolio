package org.yss.pres;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.yss.metier.IMetier;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext ctx=new AnnotationConfigApplicationContext("org.yss.dao","org.yss.metier");
        //ApplicationContext ctx=new ClassPathXmlApplicationContext("config.xml");
        IMetier metier=ctx.getBean(IMetier.class);
        System.out.println(metier.calculate());
    }
}
