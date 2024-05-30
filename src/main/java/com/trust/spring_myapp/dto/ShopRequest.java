package com.trust.spring_myapp.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * ショップ情報 リクエストデータ
 */
@Data
public class ShopRequest implements Serializable {
	/**
	 * 名前
	 */
	@NotEmpty(message = "ショップ名を入力してください")
	private String name;
	/**
	 * メールアドレス
	 */
	@NotEmpty(message = "ショップの詳細を入力してください")
	private String description;
}