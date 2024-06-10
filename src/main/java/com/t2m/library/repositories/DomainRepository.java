package com.t2m.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t2m.library.entities.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
}
