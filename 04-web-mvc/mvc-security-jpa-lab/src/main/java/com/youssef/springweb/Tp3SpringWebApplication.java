package com.youssef.springweb;

import com.youssef.springweb.entities.Product;
import com.youssef.springweb.reposetories.ProductRepository;
import com.youssef.springweb.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Tp3SpringWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp3SpringWebApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductService productService) {
        return args -> {
            productService.addProduct(new Product(null, "ordinateur", 5000, 12 ));
            productService.addProduct(new Product(null, "telephone", 2500, 5 ));
            productService.addProduct(new Product(null, "tablette", 3000, 10 ));
            productService.addProduct(new Product(null, "souris", 250, 50 ));
            productService.addProduct(new Product(null, "clavier", 500, 36 ));
            productService.addProduct(new Product(null, "chargeur", 600, 25 ));
        };
    }

}
