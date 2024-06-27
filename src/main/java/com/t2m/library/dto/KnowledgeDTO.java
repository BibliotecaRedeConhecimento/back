package com.t2m.library.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.t2m.library.entities.Category;
import com.t2m.library.entities.Knowledge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class KnowledgeDTO {

	private Long id;
	@NotBlank(message = "Campo requerido")
	private String title;
	@NotBlank(message = "Campo requerido")
	private String titleMedia;
	@NotBlank(message = "Campo requerido")
	private String introduction;
	@NotBlank(message = "Campo requerido")
	private String description;
	@NotBlank(message = "Campo requerido")
	private String collaborator;
	@NotBlank(message = "Campo requerido")
	private String archive;
	private Boolean active = true;
	private boolean needsReview = false;
	
	@NotEmpty(message = "Conhecimento sem categoria não é permitido")
	private List<CategoryDTO> categories = new ArrayList<>();

	public KnowledgeDTO() {
		this.active = true;
	}

	public KnowledgeDTO(Long id, String title, String titleMedia, String introduction, String description, String collaborator, String archive, Boolean active, Boolean needsReview) {
		this.id = id;
		this.title = title;
		this.titleMedia = titleMedia;
		this.introduction = introduction;
		this.description = description;
		this.collaborator = collaborator;
		this.archive = archive;
		this.active = active;
		this.needsReview = needsReview;
	}

	public KnowledgeDTO(Knowledge entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.titleMedia = entity.getTitleMedia();
		this.introduction = entity.getIntroduction();
		this.description = entity.getDescription();
		this.collaborator = entity.getCollaborator();
		this.archive = entity.getArchive();
		this.active = entity.getActive();
		this.needsReview = entity.getNeedsReview();
		entity.getCategories().forEach(cat -> this.categories.add(new CategoryDTO(cat, cat.getDomains())));
	}
	
	public KnowledgeDTO(Knowledge entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat, cat.getDomains())));
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

	public String getTitleMedia() {
		return titleMedia;
	}

	public void setTitleMedia(String titleMedia) {
		this.titleMedia = titleMedia;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	//Requisição para o colaborador criar conhecimento

	public boolean getNeedsReview() {return needsReview;}

	public void setNeedsReview(boolean NeedsReview) {this.needsReview = NeedsReview;}

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
