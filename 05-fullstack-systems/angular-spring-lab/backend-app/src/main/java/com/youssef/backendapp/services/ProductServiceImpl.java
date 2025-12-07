package com.youssef.backendapp.services;

import com.youssef.backendapp.entities.Product;
import com.youssef.backendapp.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor // remplace @AutoWired
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product){
        return  productRepository.save(product);
    }

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProductById(Long id, String name,double price, long quantity){
        Product productToUpdte = productRepository.findById(id).get();
        productToUpdte.setName(name);
        productToUpdte.setPrice(price);
        return productRepository.save(productToUpdte);
    }

    @Override
    public Product findById(Long id){
        return productRepository.findById(id).get();
    }

}
