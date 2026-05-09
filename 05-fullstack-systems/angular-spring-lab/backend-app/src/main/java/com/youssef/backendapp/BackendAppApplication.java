package com.youssef.backendapp;

import com.youssef.backendapp.entities.Product;
import com.youssef.backendapp.repositories.ProductRepository;
import com.youssef.backendapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AllArgsConstructor
@SpringBootApplication
public class BackendAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendAppApplication.class, args);
    }
    private ProductService productService;

    @Bean
    CommandLineRunner init(ProductService productService) {
        return args -> {
            productService.addProduct(new Product(null, "ordinateur", 5000, true ));
            productService.addProduct(new Product(null, "telephone", 2500, true ));
            productService.addProduct(new Product(null, "tablette", 3000, false ));
            productService.addProduct(new Product(null, "souris", 250, true ));
            productService.addProduct(new Product(null, "clavier", 500, false ));
            productService.addProduct(new Product(null, "chargeur", 600, true ));

            List<Product> products = productService.findAll();
            for (Product p : products) {
                System.out.println(p.toString());
            }
        };
    }

}
