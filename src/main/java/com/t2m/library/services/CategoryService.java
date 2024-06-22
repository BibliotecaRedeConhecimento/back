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
import com.t2m.library.dto.DomainDTO;
import com.t2m.library.entities.Category;
import com.t2m.library.entities.Domain;
import com.t2m.library.entities.Knowledge;
import com.t2m.library.projections.CategoryProjection;
import com.t2m.library.repositories.CategoryRepository;
import com.t2m.library.repositories.DomainRepository;
import com.t2m.library.services.exceptions.ControllerNotFoundException;
import com.t2m.library.services.exceptions.DatabaseException;
import com.t2m.library.util.Utils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repository;

	@Autowired
	private DomainRepository domainRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id not found" + id);
		}
	}
	
	@Transactional
	public CategoryDTO activate(Long id) {
		try {
			Category entity = repository.getReferenceById(id);
			
			if (!entity.getKnowledges().isEmpty()) {
                throw new IllegalStateException("Não é possível desativar esta categoria porque há conhecimentos associados");
            }

            for (Knowledge knowledge : entity.getKnowledges()) {
                if (knowledge.getActive()) {
                    throw new IllegalStateException("Não é possível desativar esta categoria porque há conhecimentos associados");
                }
            }
			
			Boolean active = entity.getActive() == true ? false : true;
			entity.setActive(active);
			entity = repository.save(entity);
			return new CategoryDTO(entity);
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
	
	private void copyDtoToEntity(CategoryDTO dto, Category entity) {

		entity.setName(dto.getName());
		entity.setActive(dto.getActive());
		
		entity.getDomains().clear();
		for (DomainDTO domDto: dto.getDomains()) {
			Domain domain = domainRepository.getReferenceById(domDto.getId());
			entity.getDomains().add(domain);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(String domainId, String name, Boolean active, Pageable pageable) {
		
		List<Long> domainIds = Arrays.asList();
		if (!"0".equals(domainId)) {
			domainIds = Arrays.asList(domainId.split(",")).stream().map(Long::parseLong).toList();
		}
		
		Page<CategoryProjection> page = repository.searchCategories(domainIds, name.trim(), active, pageable);
		List<Long> categoryIds = page.map(x -> x.getId()).toList();
		
		List<Category> entities = repository.searchCategoriesWithDomains(categoryIds);
		entities = (List<Category>) Utils.replace(page.getContent(), entities);
		List<CategoryDTO> dtos = entities.stream().map(c -> new CategoryDTO(c, c.getDomains())).toList();
		
		return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
	}
	
}
