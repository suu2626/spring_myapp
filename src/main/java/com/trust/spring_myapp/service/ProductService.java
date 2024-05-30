package com.trust.spring_myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trust.spring_myapp.dto.ProductRequest;
import com.trust.spring_myapp.entity.Product;
import com.trust.spring_myapp.entity.Shop;
import com.trust.spring_myapp.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByShop(Shop shop) {
        return productRepository.findByShop(shop);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public List<Product> findByShopId(Long shopId) {
        return productRepository.findByShopId(shopId);
    }

    public void createProduct(ProductRequest productRequest, Shop shop) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setShop(shop);
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductRequest productRequest, Shop shop) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setStock(productRequest.getStock());
            product.setShop(shop);
            productRepository.save(product);
        }
    }

    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    /**
     * 購入処理
     * @param id
     */
    @Transactional
    public void decreaseStock(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() > 0) {
            product.setStock(product.getStock() - 1);
            productRepository.save(product);
        }
    }
}