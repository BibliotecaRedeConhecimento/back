package com.t2m.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t2m.library.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
