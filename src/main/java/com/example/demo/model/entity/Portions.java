package com.example.demo.model.entity;

import java.io.Serializable;

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
@Table(name = "portions")
@IdClass(Portions.PortionsId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portions {

    @Id
    private Integer sid;

    @Id
    private Integer fid;

    @Id
    private Integer pid;

    @Column(name = "pname", nullable = false, length = 40)
    private String pname;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false),
        @JoinColumn(name = "fid", referencedColumnName = "fid", insertable = false, updatable = false)
    })
    private Fields field;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortionsId implements Serializable {
        private Integer sid;
        private Integer fid;
        private Integer pid;
    }
}