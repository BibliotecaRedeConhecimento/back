package com.t2m.library.controllers;

import com.t2m.library.dto.ActivateDTO;
import com.t2m.library.dto.DomainDTO;
import com.t2m.library.entities.Knowledge;
import org.springframework.data.domain.Pageable;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.services.KnowledgeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "knowledges")
public class KnowledgeController {

	@Autowired
	private KnowledgeService service;

	@GetMapping
	public ResponseEntity<Page<KnowledgeDTO>> findAll(Pageable pageable, @RequestParam(name="active", defaultValue = "true") String active) {
		Page<KnowledgeDTO> list = service.findAllPaged(pageable, Boolean.parseBoolean(active));
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
	@PutMapping(value = "/{id}/activate")
	public ResponseEntity<KnowledgeDTO> activate(@PathVariable Long id, @Valid @RequestBody ActivateDTO dto) {
		KnowledgeDTO ddto = service.activate(id, dto);
		return ResponseEntity.ok().body(new KnowledgeDTO());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<KnowledgeDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
