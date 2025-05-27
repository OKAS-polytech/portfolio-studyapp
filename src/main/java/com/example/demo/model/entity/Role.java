package com.example.demo.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ロールエンティティクラス
 * データベースのrolesテーブルにマッピングされる
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    /**
     * ロールID（主キー）
     * 自動採番
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * ロール名
     * 必須、一意制約あり
     */
    @Column(nullable = false, unique = true)
    private String name;
    
    /**
     * このロールを持つユーザーのセット
     * 無限ループ防止のため、ToString対象から除外
     */
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude  // 循環参照によるスタックオーバーフロー防止
    private Set<User> users = new HashSet<>();
    
 // Role.java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
               Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
