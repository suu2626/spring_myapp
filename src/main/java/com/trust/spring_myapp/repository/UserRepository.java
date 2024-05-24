package com.trust.spring_myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trust.spring_myapp.entity.User;

/**
 * ユーザー情報 Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {
}