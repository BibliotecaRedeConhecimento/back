package com.t2m.library.controllers;

import java.net.URI;

import com.t2m.library.dto.ActivateDTO;
import com.t2m.library.dto.DomainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
	public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable,  @RequestParam(name="active", defaultValue = "true") String active) {
		Page<CategoryDTO> list = service.findAllPaged(pageable, Boolean.parseBoolean(active));
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
	public ResponseEntity<CategoryDTO> activate(@PathVariable Long id, @Valid @RequestBody ActivateDTO dto) {
		CategoryDTO ddto = service.activate(id, dto);
		return ResponseEntity.ok().body(new CategoryDTO());
	}
	
	@DeleteMapping(value = "/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
