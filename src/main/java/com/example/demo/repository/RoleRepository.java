package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Role;

/**
 * ロールリポジトリインターフェース
 * Spring Data JPAによりCRUD操作を提供
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * ロール名からロールを検索
     * 
     * @param name 検索するロール名
     * @return 該当するロール（存在しない場合は空のOptional）
     */
    Optional<Role> findByName(String name);
}