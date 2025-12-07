package com.youssef.backendapp.services;

import com.youssef.backendapp.entities.Product;

import java.util.List;

public interface ProductService{
    Product addProduct(Product product);
    List<Product> findAll();
    void deleteById(Long id);
    Product updateProductById(Long id, String name,double price, long quantity );
    Product findById(Long id);
}
