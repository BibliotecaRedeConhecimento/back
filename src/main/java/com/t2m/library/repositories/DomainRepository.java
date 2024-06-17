package com.t2m.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.t2m.library.entities.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
	
	@Query("SELECT obj FROM Domain obj WHERE (LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND obj.active = :active")
	Page<Domain> searchDomains(String name, Boolean active, Pageable pageable);
}

