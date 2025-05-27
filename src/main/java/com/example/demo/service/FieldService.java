package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StudyMapper;
import com.example.demo.model.dto.FieldDto;
import com.example.demo.model.entity.Fields;
import com.example.demo.model.entity.Fields.FieldsId;
import com.example.demo.repository.FieldsRepository;

import lombok.RequiredArgsConstructor;

/**
 * Fieldsテーブルに対するビジネスロジックを提供するサービスクラス。
 */
@Service
@RequiredArgsConstructor
public class FieldService {

    // Fieldsエンティティを操作するためのリポジトリ
    private final FieldsRepository repository;

    // DTOとエンティティの相互変換用マッパー
    private final StudyMapper mapper;

    /**
     * 全てのFieldsデータをDTOのリストとして取得する。
     *
     * @return List<FieldDto> DTOリスト
     */
    public List<FieldDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 複合主キー(sid, fid)で1件のFieldデータを取得する。
     *
     * @param sid StudiesのID
     * @param fid FieldsのID
     * @return FieldDto DTO形式のデータ
     */
    public FieldDto findById(Integer sid, Integer fid) {
        Fields entity = repository.findById(new FieldsId(sid, fid))
                .orElseThrow(() -> new RuntimeException("Field not found"));
        return mapper.toDto(entity);
    }
    
    public void save(FieldDto dto) {
        Fields entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    public void deleteById(Integer sid, Integer fid) {
        repository.deleteById(new Fields.FieldsId(sid, fid));
    }
}
