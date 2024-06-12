package com.t2m.library.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.t2m.library.entities.Category;
import com.t2m.library.entities.Domain;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

	private Long id;
	 
	@NotBlank(message = "Campo requerido")
	private String name;
	private Boolean active;
	
	private List<DomainDTO> knowledges = new ArrayList<>();
	private List<DomainDTO> domains = new ArrayList<>();
	
	public CategoryDTO() {
		this.active = true;
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
		this.active = true;
	}
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.active = entity.getActive();
	}
	
	public CategoryDTO(Category entity, Set<Domain> domains) {
		this(entity);
		domains.forEach(dom -> this.domains.add(new DomainDTO(dom)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public List<DomainDTO> getKnowledges() {
		return knowledges;
	}

	public List<DomainDTO> getDomains() {
		return domains;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		return Objects.equals(id, other.id);
	}

}
