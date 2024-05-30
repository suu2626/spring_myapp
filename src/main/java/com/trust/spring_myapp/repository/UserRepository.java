package com.trust.spring_myapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trust.spring_myapp.entity.User;

/**
 * ユーザー情報 Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {
		// メールアドレスでユーザーを探すメソッド。メールアドレスをパラメータとして渡すと、そのメールアドレスを持つユーザーをデータベースから探して返す。
	Optional<User> findByEmail(String email);
}