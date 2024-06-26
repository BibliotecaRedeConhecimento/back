package com.t2m.library.services;

import java.util.Optional;

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

import filters.KnowledgeFilter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
public class KnowledgeService {
	@Autowired
	private KnowledgeRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
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
			if (!entity.getActive() && entity.getCategories().stream().anyMatch(x -> !x.getActive())){
				throw new IllegalStateException("Não é possível reativar este conhemciento porque há categorias relacionadas inativas");
			}

			if (!entity.getActive() && entity.getCategories().stream().anyMatch(x -> x.getDomains().stream().anyMatch(y -> !y.getActive()))){
				throw new IllegalStateException("Não é possível reativar este conhemciento porque há domínios relacionados inativos");
			}

			entity.setActive(!entity.getActive());
			Knowledge updated = repository.save(entity);
			return new KnowledgeDTO(updated);
		} catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id not found" + id);
		}
	}


	@Transactional
	public Page<KnowledgeDTO> NeedsReview(Pageable pageable) {
		Page<Knowledge> entities = repository.searchKnowledgesWithCategoriesAndCAndCollaboratorByNeedsReview(true, pageable);
		return entities.map(x -> new KnowledgeDTO(x));
	}

	@Transactional
	public KnowledgeDTO acceptKnowledge(Long id) {
		try {
			Knowledge entity = repository.getReferenceById(id);
			entity.setNeedsReview(false);
			entity.setActive(true);
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
		entity.setNeedsReview(dto.getNeedsReview());
		entity.setActive(dto.getActive());

		entity.getCategories().clear();
		for (CategoryDTO catDto: dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
	
	@Transactional(readOnly = true)
	public Page<KnowledgeDTO> findAllPaged(@NotNull KnowledgeFilter filter, @NotNull Pageable pageable) {
		KnowledgeRepository.KnowledgeSpecification specification = new KnowledgeRepository.KnowledgeSpecification(filter);
		Page<Knowledge> list = repository.findAll(specification, pageable);
		return list.map(x -> new KnowledgeDTO(x));
	}
}
