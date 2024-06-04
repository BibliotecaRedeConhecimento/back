package com.t2m.library.dto;

import java.util.Objects;

import com.t2m.library.entities.Category;
import com.t2m.library.entities.Knowledge;

public class KnowledgeDTO {

	private Long id;
	
	private String title;
	
	private String text;

	public KnowledgeDTO() {
	}

	public KnowledgeDTO(Long id, String title, String text) {
		this.id = id;
		this.title = title;
		this.text = text;
	}
	
	public KnowledgeDTO(Knowledge entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.text = entity.getText();
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
