package com.t2m.library.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.t2m.library.dto.DomainDTO;
import com.t2m.library.entities.Domain;
import com.t2m.library.repositories.DomainRepository;
import com.t2m.library.services.exceptions.ControllerNotFoundException;
import com.t2m.library.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DomainService {
    @Autowired
    DomainRepository repository;

    @Transactional(readOnly = true)
    public DomainDTO findById(Long id) {
        Optional<Domain> obj = repository.findById(id);
        Domain entity = obj.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
        return new DomainDTO(entity);
    }


    @Transactional(readOnly = true)
    public Page<DomainDTO> findAllPaged(String name, Boolean active, Pageable pageable) {
        Page<Domain> list = repository.searchDomains(name, active, pageable);
        return list.map(x -> new DomainDTO(x));
    }

    @Transactional
    public DomainDTO insert(DomainDTO dto) {
        Domain entity = new Domain();
        entity.setName(dto.getName());
        entity = (Domain) repository.save(entity);
        return new DomainDTO(entity);
    }
    @Transactional
    public DomainDTO update(Long id, DomainDTO dto) {
        try {
            Domain entity = (Domain) repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = (Domain) repository.save(entity);
            entity.setActive(dto.isActive());
            return new DomainDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Id not found" + id);
        }
    }
    
    @Transactional
	public DomainDTO activate(Long id) {
		try {
			Domain entity = repository.getReferenceById(id);
            if (entity.getActive() && entity.getCategories().stream().anyMatch(x -> x.getActive())) {
                throw new IllegalStateException("Não é possível desativar este domínio porque há categorias associadas");
            }

			entity.setActive(!entity.getActive());
			entity = repository.save(entity);
			return new DomainDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id not found" + id);
		}
	}
    
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ControllerNotFoundException("Id");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    
}
