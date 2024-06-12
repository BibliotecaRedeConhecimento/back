package com.t2m.library.services;

import java.util.Optional;

import com.t2m.library.dto.ActivateDTO;
import com.t2m.library.dto.DomainDTO;
import com.t2m.library.entities.Domain;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.t2m.library.dto.CategoryDTO;
import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.entities.Category;
import com.t2m.library.entities.Knowledge;
import com.t2m.library.repositories.CategoryRepository;
import com.t2m.library.repositories.KnowledgeRepository;
import com.t2m.library.services.exceptions.ControllerNotFoundException;
import com.t2m.library.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class KnowledgeService {
	@Autowired
	private KnowledgeRepository repository;
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<KnowledgeDTO> findAllPaged(Pageable pageable, boolean isActive) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("activeKnowledgeFilter");
		filter.setParameter("isActive", isActive);
		Page<Knowledge> list = repository.findAll(pageable);
		return list.map(x -> new KnowledgeDTO(x));
	}

	@Transactional(readOnly = true)
	public KnowledgeDTO findById(Long id) {
		Optional<Knowledge> obj = repository.findById(id);
		Knowledge entity = obj.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		return new KnowledgeDTO(entity);
	}

	@Transactional
	public KnowledgeDTO insert(KnowledgeDTO dto) {
		Knowledge entity = new Knowledge();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new KnowledgeDTO(entity);
	}

	@Transactional
	public KnowledgeDTO update(Long id, KnowledgeDTO dto) {
		try {
			Knowledge entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new KnowledgeDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id not found" + id);
		}
	}
	@Transactional
	public KnowledgeDTO activate(Long id, ActivateDTO dto) {
		try {
			Knowledge entity = (Knowledge) repository.getReferenceById(id);
			entity.setActive(dto.isActivated());
			entity = (Knowledge) repository.save(entity);
			return new KnowledgeDTO(entity);
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
	
	private void copyDtoToEntity(KnowledgeDTO dto, Knowledge entity) {

		entity.setTitle(dto.getTitle());
		entity.setText(dto.getText());
		entity.setArchive(dto.getArchive());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto: dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
}
