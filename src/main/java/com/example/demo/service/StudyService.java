package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StudyMapper;
import com.example.demo.model.dto.StudyDto;
import com.example.demo.model.entity.Studies;
import com.example.demo.repository.StudiesRepository;

import lombok.RequiredArgsConstructor;

/**
 * Studiesテーブルに対するビジネスロジックを提供するサービスクラス。
 */
@Service
@RequiredArgsConstructor
public class StudyService {

    // Studiesエンティティの永続化を扱うリポジトリ
    private final StudiesRepository repository;

    // Entity <-> DTO の変換を行うMapStructマッパー
    private final StudyMapper mapper;

    /**
     * 全てのStudiesデータを取得し、DTOのリストとして返す。
     *
     * @return List<StudyDto> 変換後のDTOリスト
     */
	public List<StudyDto> findAll() {
	    return repository.findAll().stream()
	            .map(mapper::toDto)
	            .collect(Collectors.toList());
	}

    /**
     * 指定されたIDのStudyを1件取得する。
     *
     * @param sid 主キー
     * @return StudyDto 対応するDTO
     * @throws RuntimeException 見つからなかった場合に例外をスロー
     */
    public StudyDto findById(Integer sid) {
        Studies entity = repository.findById(sid)
                .orElseThrow(() -> new RuntimeException("Study not found"));
        return mapper.toDto(entity);
    }
    
    public void save(StudyDto dto) {
        Studies entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    public void deleteById(Integer sid) {
        repository.deleteById(sid);
    }
}
