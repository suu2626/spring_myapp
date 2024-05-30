package com.trust.spring_myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trust.spring_myapp.entity.Shop;
import com.trust.spring_myapp.entity.User;

/**
 * ユーザー情報 Repository
 */
public interface ShopRepository extends JpaRepository<Shop, Long> {
	List<Shop> findByUser(User user);
}