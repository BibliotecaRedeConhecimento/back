package com.t2m.library.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.t2m.library.entities.Category;
import com.t2m.library.entities.Knowledge;

import jakarta.validation.constraints.NotBlank;

public class KnowledgeDTO {

	private Long id;
	@NotBlank(message = "Campo requerido")
	private String title;
	@NotBlank(message = "Campo requerido")
	private String text;
	@NotBlank(message = "Campo requerido")
	private String archive;
	
	private List<CategoryDTO> categories = new ArrayList<>();

	public KnowledgeDTO() {
	}

	public KnowledgeDTO(Long id, String title, String text, String archive) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.archive = archive;
	}

	public KnowledgeDTO(Knowledge entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.text = entity.getText();
		this.archive = entity.getArchive();
	}
	
	public KnowledgeDTO(Knowledge entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}
	
	public List<CategoryDTO> getCategories() {
		return categories;
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
		KnowledgeDTO other = (KnowledgeDTO) obj;
		return Objects.equals(id, other.id);
	}

}
