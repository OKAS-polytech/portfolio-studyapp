package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login"; // src/main/resources/templates/login.html を表示
    }
    
    @GetMapping("/")
    public String home() {
		return "home"; // ログイン画面にリダイレクト
	}
}