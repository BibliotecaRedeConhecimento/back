package com.t2m.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.t2m.library.entities.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{

}
