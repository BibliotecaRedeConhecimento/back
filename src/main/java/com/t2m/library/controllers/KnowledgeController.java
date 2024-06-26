package com.t2m.library.controllers;

import java.net.URI;

import filters.KnowledgeFilter;
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

import com.t2m.library.dto.KnowledgeDTO;
import com.t2m.library.services.KnowledgeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "knowledges")
public class KnowledgeController {

	@Autowired
	private KnowledgeService service;
	
	@GetMapping
	public ResponseEntity<Page<KnowledgeDTO>> findAllPaged(
			@RequestParam(value = "domainId", required = false) String domainId,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "active", defaultValue = "true") Boolean active,
			@RequestParam(value = "needsReview", defaultValue = "false") boolean needsReview,
			Pageable pageable) {
		Page<KnowledgeDTO> list = service.findAllPaged(new KnowledgeFilter(
				title,
				categoryId,
				domainId,
				active,
				needsReview),
			pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/review")
	public ResponseEntity<Page<KnowledgeDTO>> review(Pageable pageable) {
		Page<KnowledgeDTO> dto = service.NeedsReview(pageable);
		return ResponseEntity.ok().body(dto);
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
	
	@PutMapping(value = "/activate/{id}")
	public ResponseEntity<KnowledgeDTO> activate(@PathVariable Long id) {
		service.activate(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/accept/{id}")
	public ResponseEntity<KnowledgeDTO> accept(@PathVariable Long id) {
		service.acceptKnowledge(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<KnowledgeDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
