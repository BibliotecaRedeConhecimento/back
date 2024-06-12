package com.t2m.library.controllers;

import com.t2m.library.dto.ActivateDTO;
import com.t2m.library.dto.DomainDTO;
import com.t2m.library.services.DomainService;
import	com.t2m.library.dto.DomainDTO.SuccessMenssageDTO ;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "domains")
public class DomainController {

	@Autowired
	DomainService service;

	@GetMapping
	public ResponseEntity<Page<DomainDTO>> findAll(Pageable pageable, @RequestParam(name="active", defaultValue = "true") String active) {
		Page<DomainDTO> list = service.findAllPaged(pageable, Boolean.parseBoolean(active));
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
		dto = service.update(id, dto);
        return ResponseEntity.ok().body(new SuccessMenssageDTO());
	}

	@PutMapping(value = "/{id}/activate")
	public ResponseEntity<SuccessMenssageDTO> activate(@PathVariable Long id, @Valid @RequestBody ActivateDTO dto) {
		DomainDTO ddto = service.activate(id, dto);
		return ResponseEntity.ok().body(new SuccessMenssageDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessMenssageDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return  ResponseEntity.noContent().build();
	}

}
