package com.trust.spring_myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trust.spring_myapp.entity.Product;
import com.trust.spring_myapp.entity.Shop;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShop(Shop shop);
    List<Product> findByShopId(Long shopId);
}
