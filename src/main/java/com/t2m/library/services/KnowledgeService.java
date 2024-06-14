package com.t2m.library.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.t2m.library.dto.CategoryDTO;
import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.entities.Category;
import com.t2m.library.entities.Knowledge;
import com.t2m.library.projections.KnowledgeProjection;
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
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<KnowledgeDTO> findAllPaged(String categoryId, String title, Boolean active, Pageable pageable) {
		
		List<Long> categoryIds = Arrays.asList();
		if (!"0".equals(categoryId)) {
			categoryIds = Arrays.asList(categoryId.split(",")).stream().map(Long::parseLong).toList();
		}
		
		Page<KnowledgeProjection> page = repository.searchKnowledges(categoryIds, title, active, pageable);
		List<Long> knowledgeIds = page.map(x -> x.getId()).toList();
		
		List<Knowledge> entities = repository.searchKnowledgesWithCategories(knowledgeIds);
		List<KnowledgeDTO> dtos = entities.stream().map(k -> new KnowledgeDTO(k, k.getCategories())).toList();
		
		Page<KnowledgeDTO> pageDto = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
		return pageDto;
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
	public KnowledgeDTO activate(Long id) {
		try {
			Knowledge entity = repository.getReferenceById(id);
			Boolean active = entity.getActive() == true ? false : true;
			entity.setActive(active);
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
	
	private void copyDtoToEntity(KnowledgeDTO dto, Knowledge entity) {

		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setArchive(dto.getArchive());
		entity.setTitleMedia(dto.getTitleMedia());
		entity.setIntroduction(dto.getIntroduction());
		entity.setCollaborator(dto.getCollaborator());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto: dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
	
	
}
