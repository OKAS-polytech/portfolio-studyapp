package com.example.demo.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fields")
@IdClass(Fields.FieldsId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fields {

    @Id
    private Integer sid;

    @Id
    private Integer fid;

    @Column(name = "fname", nullable = false, length = 30)
    private String fname;

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    private Studies study;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldsId implements Serializable {
        private Integer sid;
        private Integer fid;
    }
}