package com.example.demo.service;

import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー関連の業務ロジックを提供するサービスクラス
 */
@Service
@RequiredArgsConstructor
public class UserService {

    // コンストラクタインジェクション
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 新規ユーザーを登録する
     * 
     * @param username ユーザー名
     * @param password パスワード（平文）
     * @param roleNames 付与するロール名の配列
     * @return 登録されたユーザーエンティティ
     * @throws RuntimeException ユーザー名が既に使用されている場合
     */
    @Transactional
    public User registerNewUser(String username, String password, String... roleNames) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("ユーザー名はすでに使用されています: " + username);
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(new HashSet<>()); // 初期化
        
        for (String roleName : roleNames) {
            // ここで "GENERAL" や "ADMIN" を使う
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        return roleRepository.save(newRole);
                    });
            user.addRole(role);
        }
        
        return userRepository.save(user);
    }
}
