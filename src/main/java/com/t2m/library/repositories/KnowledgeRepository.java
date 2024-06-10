package com.t2m.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t2m.library.entities.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{
	
	@Query(value = "SELECT obj FROM Knowledge obj JOIN FETCH obj.categories",
			countQuery = "SELECT COUNT(obj) FROM Knowledge obj JOIN obj.categories")
	Page<Knowledge> findAll(Pageable pageable);
}
