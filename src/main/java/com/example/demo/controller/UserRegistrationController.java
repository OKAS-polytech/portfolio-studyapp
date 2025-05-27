package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.UserRegistrationDto;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録機能のコントローラクラス
 */
@Controller
@RequiredArgsConstructor
public class UserRegistrationController {

    // コンストラクタインジェクション
    private final UserService userService;
    
    /**
     * 登録フォーム表示
     * 
     * @param model モデル
     * @return 登録フォームのビュー名
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // 空のDTOをモデルに追加
        model.addAttribute("user", new UserRegistrationDto());
        return "register";  // register.htmlを表示
    }
    
    /**
     * ユーザー登録処理
     * 
     * @param registrationDto 登録フォームから送信されたデータ
     * @return リダイレクト先
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        // ユーザーサービスを使用して登録処理を実行
        userService.registerNewUser(
                registrationDto.getUsername(), 
                registrationDto.getPassword(), 
                "GENERAL"  // デフォルトロール
        );
        // 登録完了後、登録完了メッセージ付きでログイン画面にリダイレクト
        return "redirect:/login?registered";
    }
}