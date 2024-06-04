package com.t2m.library.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.entities.Knowledge;
import com.t2m.library.repositories.KnowledgeRepository;
import com.t2m.library.services.exceptions.ControllerNotFoundException;
import com.t2m.library.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class KnowledgeService {
	@Autowired
	KnowledgeRepository repository;
	
	@Transactional(readOnly = true)
	public Page<KnowledgeDTO> findAllPaged(Pageable pageable) {
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
		entity.setTitle(dto.getTitle());
		entity.setText(dto.getText());
		entity = repository.save(entity);
		return new KnowledgeDTO(entity);
	}

	@Transactional
	public KnowledgeDTO update(Long id, KnowledgeDTO dto) {
		try {
			Knowledge entity = repository.getReferenceById(id);
			entity.setTitle(dto.getTitle());
			entity.setText(dto.getText());
			entity = repository.save(entity);
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
}
