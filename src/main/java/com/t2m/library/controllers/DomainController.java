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

import com.t2m.library.dto.DomainDTO;
import	com.t2m.library.dto.DomainDTO.SuccessMenssageDTO ;
import com.t2m.library.services.DomainService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "domains")
public class DomainController {

	@Autowired
	DomainService service;

	@GetMapping
	public ResponseEntity<Page<DomainDTO>> findAllPaged(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "active", defaultValue = "true") Boolean active,
			Pageable pageable) {
		Page<DomainDTO> list = service.findAllPaged(name, active, pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DomainDTO> findById(@PathVariable Long id) {
		DomainDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<SuccessMenssageDTO> insert(@Valid @RequestBody DomainDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(new SuccessMenssageDTO());
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<SuccessMenssageDTO> update(@PathVariable Long id, @Valid @RequestBody DomainDTO dto) {
		if (dto.isActive() == null) {
			dto.setActive(true);
		}
		dto = service.update(id, dto);
        return ResponseEntity.ok().body(new SuccessMenssageDTO());
	}

	@PutMapping(value = "/activate/{id}")
	public ResponseEntity<SuccessMenssageDTO> activate(@PathVariable Long id) {
		service.activate(id);
		return ResponseEntity.ok().body(new SuccessMenssageDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessMenssageDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return  ResponseEntity.noContent().build();
	}

}
