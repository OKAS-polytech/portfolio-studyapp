package com.example.demo.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articles")
@IdClass(Articles.ArticlesId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Articles {

    @Id
    private Integer sid;

    @Id
    private Integer fid;

    @Id
    private Integer pid;

    @Id
    private Integer aid;

    @Column(name = "atitle", nullable = false, length = 40)
    private String atitle;

    @Column(name = "atext", nullable = false, columnDefinition = "TEXT")
    private String atext;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false),
        @JoinColumn(name = "fid", referencedColumnName = "fid", insertable = false, updatable = false),
        @JoinColumn(name = "pid", referencedColumnName = "pid", insertable = false, updatable = false)
    })
    private Portions portion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticlesId implements Serializable {
        private Integer sid;
        private Integer fid;
        private Integer pid;
        private Integer aid;
    }
}
