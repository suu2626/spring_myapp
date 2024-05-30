package com.trust.spring_myapp.entity;
	
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
	
	/**
	 * ショップ情報 Entity
	 */
	@Entity
	@Data
	@Table(name = "users")
	public class User {
	
	    /**
	     * ID
	     */
	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    /**
	     * 名前
	     */
	    @Column(name = "name")
	    private String name;
	
	    /**
	     * メールアドレス	
	     */
	    @Column(name = "email")
	    private String email;
	    
	    /**
	     * メールアドレス	
	     */
	    @Column(name = "password")
	    private String password;
	    
	    /**
	     * 登録日時
	     */
	    @Column(name = "created_at")
	    private Date createdAt;
	    
	    /**
	     * 更新日時
	     */
	    @Column(name = "updated_at")
	    private Date updatedAt;
	}