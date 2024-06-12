package com.t2m.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.t2m.library.entities.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{
	
	Page<Knowledge> findAll(Pageable pageable);
}
