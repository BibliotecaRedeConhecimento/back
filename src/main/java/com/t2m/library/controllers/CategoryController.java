package com.t2m.library.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.t2m.library.dto.CategoryDTO;
import com.t2m.library.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "categories")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAllPaged(
			@RequestParam(value = "domainId", defaultValue = "0") String domainId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "active", defaultValue = "true") Boolean active,
			Pageable pageable) {
		Page<CategoryDTO> list = service.findAllPaged(domainId, name, active, pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Long id, @RequestBody CategoryDTO dto) {

		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}/activate")
	public ResponseEntity<CategoryDTO> activate(@PathVariable Long id) {
		service.activate(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
