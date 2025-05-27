package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
/**
 * Spring Security設定クラス
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // コンストラクタインジェクション
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * セキュリティフィルターチェーンの設定
     * 認証・認可の設定を行う
     *
     * @param http HttpSecurityオブジェクト
     * @return 設定済みのSecurityFilterChain
     * @throws Exception 設定中に例外が発生した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRFプロテクションを有効（デフォルト）
            .csrf(csrf -> csrf.disable())  // 必要に応じて有効/無効を切り替え
            
            // URLごとのアクセス権限設定
            .authorizeHttpRequests(auth -> auth
                // 静的リソースは認証不要
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                // 認証関連ページは認証不要
                .requestMatchers("/login", "/register", "/signup").permitAll()
                // 管理者専用ページ
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // その他のリクエストは認証必要
                .anyRequest().authenticated()
            )
            
            // フォームログイン設定
            .formLogin(form -> form
                // カスタムログインページ
                .loginPage("/login")
                // ログイン処理URL（デフォルトは/login）
                .loginProcessingUrl("/login")
                // ログイン成功時のリダイレクト先
                .defaultSuccessUrl("/", true)
                // ログイン失敗時のリダイレクト先
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            // ログアウト設定
            .logout(logout -> logout
                // ログアウト処理URL（デフォルトは/logout）
                .logoutUrl("/logout")
                // ログアウト成功時のリダイレクト先
                .logoutSuccessUrl("/login?logout")
                // セッション無効化
                .invalidateHttpSession(true)
                // クッキークリア
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }
    
    /**
     * 認証プロバイダーの設定
     * UserDetailsServiceとPasswordEncoderを使用
     *
     * @return 設定済みのDaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // カスタムUserDetailsServiceを設定
        authProvider.setUserDetailsService(userDetailsService);
        // パスワードエンコーダーを設定
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}