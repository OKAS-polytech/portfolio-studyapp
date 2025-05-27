package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードエンコーダーの設定クラス
 */
@Configuration
public class PasswordEncoderConfig {
    
    /**
     * BCryptPasswordEncoderをBean定義
     * Spring Securityでパスワードのハッシュ化に使用される
     *
     * @return パスワードエンコーダー
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptアルゴリズムでパスワードをハッシュ化
        return new BCryptPasswordEncoder();
    }
}
