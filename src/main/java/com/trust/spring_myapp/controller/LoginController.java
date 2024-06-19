package com.trust.spring_myapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // このクラスがWebコントローラーであることを示す
public class LoginController {

    @GetMapping("/login") // "/login"というURLに対するGETリクエストを処理
    public String login() {
        return "login";  // login.htmlを表示
    }
    
    @GetMapping("/") // ルートURL ("/") に対するGETリクエストを処理
    public String redirectToIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 現在のユーザーの認証情報を取得
        if (authentication != null && authentication.isAuthenticated()) { // ユーザーがログインしている場合
            return "redirect:/index";  // "/index"にリダイレクト
        }
        return "redirect:/login"; // ユーザーがログインしていない場合、"/login"にリダイレクト
    }
    
    @GetMapping("/index") // "/index"というURLに対するGETリクエストを処理
    public String index() {
        return "index"; // index.htmlを表示
    }
}
