package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StudyMapper;
import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.entity.Articles;
import com.example.demo.repository.ArticlesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticlesRepository articleRepository;
    private final StudyMapper mapper;

    // 全記事を取得してDTOに変換して返す
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    // 指定されたIDの記事を取得し、DTOに変換して返す
    public ArticleDto findById(Integer sid, Integer fid, Integer pid, Integer aid) {
        return articleRepository.findById(new Articles.ArticlesId(sid, fid, pid, aid))
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    // atextに部分一致する記事を検索
    public List<ArticleDto> searchByAtext(String keyword) {
        return articleRepository.findByAtextContaining(keyword).stream()
                .map(mapper::toDto)
                .toList();
    }

    // atitleに部分一致する記事を検索
    public List<ArticleDto> searchByAtitle(String keyword) {
        return articleRepository.findByAtitleContaining(keyword).stream()
                .map(mapper::toDto)
                .toList();
    }
    
    public void save(ArticleDto dto) {
        Articles entity = mapper.toEntity(dto);
        articleRepository.save(entity);
    }

    public void deleteById(Integer sid, Integer fid, Integer pid, Integer aid) {
    	articleRepository.deleteById(new Articles.ArticlesId(sid, fid, pid, aid));
    }
}
