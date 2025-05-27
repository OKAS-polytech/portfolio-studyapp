package com.example.demo.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * カスタムUserDetailsService実装クラス
 * Spring Securityの認証処理で使用される
 */
@Service
@RequiredArgsConstructor  // finalフィールドを引数とするコンストラクタを生成
public class CustomUserDetailsService implements UserDetailsService {

    // コンストラクタインジェクション
    private final UserRepository userRepository;
    
    /**
     * ユーザー名からUserDetailsを読み込む
     * Spring Securityの認証プロセスで使用される
     *
     * @param username 認証対象のユーザー名
     * @return UserDetails オブジェクト
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ユーザー名からユーザーを検索
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + username));
        
        // Spring SecurityのUserオブジェクトを返す
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,  // アカウント有効期限切れでない
                true,  // 資格情報有効期限切れでない
                true,  // アカウントロックされていない
                getAuthorities(user)
        );
    }
    
    /**
     * ユーザーのロールから権限リストを生成する
     *
     * @param user ユーザーエンティティ
     * @return 権限のコレクション
     */
    private Collection<SimpleGrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
}
