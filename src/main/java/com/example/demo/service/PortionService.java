package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StudyMapper;
import com.example.demo.model.dto.PortionDto;
import com.example.demo.model.entity.Portions;
import com.example.demo.model.entity.Portions.PortionsId;
import com.example.demo.repository.PortionsRepository;

import lombok.RequiredArgsConstructor;

/**
 * Portionsテーブルに対するビジネスロジックを提供するサービスクラス。
 */
@Service
@RequiredArgsConstructor
public class PortionService {

    // Portionsエンティティのリポジトリ
    private final PortionsRepository repository;

    // エンティティとDTOの変換を行うマッパー
    private final StudyMapper mapper;

    /**
     * 全てのPortionsデータをDTOとして返す。
     *
     * @return List<PortionDto> 全データのDTOリスト
     */
    public List<PortionDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 複合主キー(sid, fid, pid)で1件のPortionデータを取得する。
     *
     * @param sid StudiesのID
     * @param fid FieldsのID
     * @param pid PortionsのID
     * @return PortionDto 対応するDTO
     */
    public PortionDto findById(Integer sid, Integer fid, Integer pid) {
        Portions entity = repository.findById(new PortionsId(sid, fid, pid))
                .orElseThrow(() -> new RuntimeException("Portion not found"));
        return mapper.toDto(entity);
    }
    
    public void save(PortionDto dto) {
        Portions entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    public void deleteById(Integer sid, Integer fid, Integer pid) {
        repository.deleteById(new Portions.PortionsId(sid, fid, pid));
    }
}