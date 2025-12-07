package com.youssef.backendapp.repositories;

import com.youssef.backendapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
