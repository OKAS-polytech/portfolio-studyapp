package com.example.demo.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーエンティティクラス
 * データベースのusersテーブルにマッピングされる
 */
@Entity
@Table(name = "users")
@Data  // getter, setter, equals, hashCode, toStringを自動生成
@NoArgsConstructor  // 引数なしコンストラクタ
@AllArgsConstructor  // 全フィールドを引数に持つコンストラクタ
public class User {
    
    /**
     * ユーザーID（主キー）
     * 自動採番
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * ユーザー名
     * 必須、一意制約あり
     */
    @Column(nullable = false, unique = true)
    private String username;
    
    /**
     * パスワード（暗号化して保存）
     * 必須
     */
    @Column(nullable = false)
    private String password;
    
    /**
     * アカウント有効フラグ
     * デフォルトはtrue（有効）
     */
    private boolean enabled = true;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    public void addRole(Role role) {
	    this.getRoles().add(role);
	    role.getUsers().add(this);
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
    
}