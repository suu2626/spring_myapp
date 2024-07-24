package com.trust.spring_myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean // このメソッドの返り値をSpringのBeanとして登録します
	public PasswordEncoder passwordEncoder() { // パスワードエンコーダー（パスワードのハッシュ化）を提供するメソッド
		return new BCryptPasswordEncoder(); // パスワードをBCrypt方式でハッシュ化するエンコーダーを返す
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests // 認証リクエストを設定
				.requestMatchers("/login", "/user/add", "/user/create").permitAll() // "/login"と"/user/add"へのリクエストは認証なしで許可
				.anyRequest().authenticated() // それ以外の全てのリクエストは認証が必要
				)
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.defaultSuccessUrl("/shop/list", true)
						.permitAll()
				)
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		                .logoutSuccessUrl("/login?logout")
		                .permitAll()// ログアウトのリクエストURLを設定
				);

		return http.build();
	}

}
