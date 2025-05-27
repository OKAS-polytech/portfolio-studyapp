package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Studies;

@Repository
public interface StudiesRepository extends JpaRepository<Studies, Integer>{
	
}
