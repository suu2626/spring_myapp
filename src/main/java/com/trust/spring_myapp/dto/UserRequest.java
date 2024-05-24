package com.trust.spring_myapp.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class UserRequest implements Serializable {
	/**
	 * 名前
	 */
	@NotEmpty(message = "名前を入力してください")
	private String name;
	/**
	 * メールアドレス
	 */
	@NotEmpty(message = "メールアドレスを入力してください")
	private String email;
	/**
	 * パスワード
	 */
	@NotEmpty(message = "パスワードを入力してください")
	private String password;
}