package com.trust.spring_myapp.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * ショップ情報 Entity
 */
@Entity
@Data
@Table(name = "shops")
public class Shop {

	/**
	 * ID
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products;

	/**
	 * 名前
	 */
	@Column(name = "name")
	private String name;

	/**
	* ショップ情報
	*/
	@Column(name = "description")
	private String description;

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