package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Portions;

@Repository
public interface PortionsRepository extends JpaRepository<Portions, Portions.PortionsId>{
	// 特定の field に属する章一覧
    List<Portions> findBySidAndFid(Integer sid, Integer fid);
}
