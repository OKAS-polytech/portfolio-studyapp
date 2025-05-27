package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.User;

/**
 * ユーザーリポジトリインターフェース
 * Spring Data JPAによりCRUD操作を提供
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * ユーザー名からユーザーを検索
     * 
     * @param username 検索するユーザー名
     * @return 該当するユーザー（存在しない場合は空のOptional）
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 指定したユーザー名が既に存在するか確認
     * 
     * @param username 確認するユーザー名
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
    boolean existsByUsername(String username);
}
