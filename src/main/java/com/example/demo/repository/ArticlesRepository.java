package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Articles;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Articles.ArticlesId>{
	// 特定の portion に属する記事一覧を作成日時順で取得
    List<Articles> findBySidAndFidAndPidOrderByCreatedAtDesc(Integer sid, Integer fid, Integer pid);

    // タイトル検索（部分一致）
    List<Articles> findByAtitleContaining(String keyword);
    
    // 記事の内容検索（部分一致）
    List<Articles> findByAtextContaining(String keyword);
}
