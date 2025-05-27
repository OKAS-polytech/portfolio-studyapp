package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー登録フォームのデータを保持するDTOクラス
 * Data Transfer Object（データ転送オブジェクト）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    
    /**
     * ユーザー名
     */
    private String username;
    
    /**
     * パスワード（平文）
     */
    private String password;
}