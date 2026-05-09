package com.youssef.backendapp.web;

import com.youssef.backendapp.entities.Product;
import com.youssef.backendapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api") // Pour eviter le conflit avec ThymeLeaf
@AllArgsConstructor
public class ProductRestAPI {
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable(name = "id")Long id){
        return productService.findById(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }

}
