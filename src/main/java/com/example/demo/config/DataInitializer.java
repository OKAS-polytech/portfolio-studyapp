package com.example.demo.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataInitializer {
    /**
     * 初期データを挿入するためのCommandLineRunnerを定義
     */
    @Bean
    public CommandLineRunner initializeData(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        return args -> {
            // テーブルにデータが既に存在するかチェック
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            Integer roleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM roles", Integer.class);

            if (userCount == 0 && roleCount == 0) {
                System.out.println("初期データを挿入します…");

                // ロールのデータを挿入
                jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "ADMIN");
                jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "GENERAL");

                // ロールのIDを取得
                Long adminRoleId = jdbcTemplate.queryForObject(
                        "SELECT id FROM roles WHERE name = ?", Long.class, "ADMIN");
                Long generalRoleId = jdbcTemplate.queryForObject(
                        "SELECT id FROM roles WHERE name = ?", Long.class, "GENERAL");

                // ユーザーデータを挿入
                // 管理者ユーザー
                String adminUsername = "admin";
                String adminPassword = passwordEncoder.encode("adminPass");
                jdbcTemplate.update(
                        "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                        adminUsername, adminPassword, true);

                // 一般ユーザー
                String regularUsername = "user";
                String regularPassword = passwordEncoder.encode("userPass");
                jdbcTemplate.update(
                        "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                        regularUsername, regularPassword, true);

                // テスト用無効ユーザー
                String disabledUsername = "disabled";
                String disabledPassword = passwordEncoder.encode("disabledPass");
                jdbcTemplate.update(
                        "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                        disabledUsername, disabledPassword, false);

                // ユーザーIDを取得
                Long adminUserId = jdbcTemplate.queryForObject(
                        "SELECT id FROM users WHERE username = ?", Long.class, adminUsername);
                Long regularUserId = jdbcTemplate.queryForObject(
                        "SELECT id FROM users WHERE username = ?", Long.class, regularUsername);
                Long disabledUserId = jdbcTemplate.queryForObject(
                        "SELECT id FROM users WHERE username = ?", Long.class, disabledUsername);

                // ユーザーとロールを関連付け
                // adminユーザーには両方のロールを付与
                jdbcTemplate.update(
                        "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                        adminUserId, adminRoleId);
                jdbcTemplate.update(
                        "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                        adminUserId, generalRoleId);

                // 一般ユーザーにはGENERALロールのみ付与
                jdbcTemplate.update(
                        "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                        regularUserId, generalRoleId);

                // 無効ユーザーにもGENERALロールを付与
                jdbcTemplate.update(
                        "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                        disabledUserId, generalRoleId);

                System.out.println("初期データ挿入完了");

                // 確認用に挿入したデータを表示
                System.out.println("---------- 登録ユーザー一覧 ----------");
                List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
                users.forEach(System.out::println);

                System.out.println("---------- 登録ロール一覧 ----------");
                List<Map<String, Object>> roles = jdbcTemplate.queryForList("SELECT * FROM roles");
                roles.forEach(System.out::println);

                System.out.println("---------- ユーザーロール関連 ----------");
                List<Map<String, Object>> userRoles = jdbcTemplate.queryForList(
                        "SELECT u.username, r.name FROM users u " +
                                "JOIN user_roles ur ON u.id = ur.user_id " +
                                "JOIN roles r ON r.id = ur.role_id");
                userRoles.forEach(System.out::println);
            } else {
                System.out.println("データベースにすでにデータが存在するため、初期データの挿入はスキップします。");
            }
        };
    }
}
