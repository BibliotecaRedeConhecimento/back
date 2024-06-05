package com.t2m.library.controllers;

import org.springframework.data.domain.Pageable;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.services.KnowledgeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "knowledges")
public class KnowledgeController {

	@Autowired
	KnowledgeService service;

	@GetMapping
	public ResponseEntity<Page<KnowledgeDTO>> fidAllKnowledge(Pageable pageable) {
		Page<KnowledgeDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<KnowledgeDTO> findById(@PathVariable Long id) {
		KnowledgeDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<KnowledgeDTO> insert(@Valid @RequestBody KnowledgeDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<KnowledgeDTO> update(@Valid @PathVariable Long id, @RequestBody KnowledgeDTO dto) {

		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<KnowledgeDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
