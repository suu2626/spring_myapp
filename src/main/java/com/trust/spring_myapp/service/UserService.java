package com.trust.spring_myapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trust.spring_myapp.dto.UserRequest;
import com.trust.spring_myapp.dto.UserUpdateRequest;
import com.trust.spring_myapp.entity.User;
import com.trust.spring_myapp.repository.UserRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public List<User> searchAll() {
		return userRepository.findAll();
	}

	/**
	   * ユーザー情報 新規登録
	   * @param user ユーザー情報
	   */
	public void create(UserRequest userRequest) {
		Date now = new Date();
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setCreatedAt(now);
		user.setUpdatedAt(now);
		userRepository.save(user);
	}

	/**
	 * ユーザー情報 主キー検索
	 * @return 検索結果
	 */
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	/**
	   * ユーザー情報 更新
	   * @param user ユーザー情報
	   */
	public void update(UserUpdateRequest userUpdateRequest) {
		User user = findById(userUpdateRequest.getId());
		user.setName(userUpdateRequest.getName());
		user.setEmail(userUpdateRequest.getEmail());
		user.setPassword(userUpdateRequest.getPassword());
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		userRepository.save(user);
	}

	/**
	 * ユーザー情報 物理削除
	 * @param id ユーザーID
	 */
	public void delete(Long id) {
		User user = findById(id);
		userRepository.delete(user);
	}
}