package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.dto.FieldDto;
import com.example.demo.model.dto.PortionDto;
import com.example.demo.model.dto.StudyDto;
import com.example.demo.model.entity.Articles;
import com.example.demo.model.entity.Fields;
import com.example.demo.model.entity.Portions;
import com.example.demo.model.entity.Studies;

@Mapper(componentModel = "spring")
public interface StudyMapper {
    // Studies
    StudyDto toDto(Studies entity);
    Studies toEntity(StudyDto dto);

    // Fields
    FieldDto toDto(Fields entity);
    Fields toEntity(FieldDto dto);

    // Portions
    PortionDto toDto(Portions entity);
    Portions toEntity(PortionDto dto);

    // Articles
    ArticleDto toDto(Articles entity);
    Articles toEntity(ArticleDto dto);
}
