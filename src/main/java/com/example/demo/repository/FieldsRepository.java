package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Fields;

@Repository
public interface FieldsRepository extends JpaRepository<Fields, Fields.FieldsId>{
	// 特定の study に属する分野一覧
	List<Fields> findBySid(Integer sid);
}
