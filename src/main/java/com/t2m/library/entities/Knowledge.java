package com.t2m.library.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tb_knowledge")
@SQLDelete(sql = "UPDATE tb_knowledge SET active = false WHERE id=?")
@FilterDef(name = "activeKnowledgeFilter", parameters = @ParamDef(name = "isActive", type = boolean.class))
@Filter(name = "activeKnowledgeFilter", condition = "active = :isActive")
public class Knowledge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String titleImag;
	private String imag;
	private String introduction;
	private String description;
	private String collaborator;
	private String archive;
	private boolean active = true;

	@ManyToMany
	@JoinTable(name = "tb_knowledge_category", joinColumns = @JoinColumn(name = "knowledge_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	public Knowledge(Long id, String title, String titleImag,String imag, String introduction, String description, String collaborator, String archive) {
		this.id = id;
		this.title = title;
		this.titleImag = titleImag;
		this.imag = imag;
		this.introduction = introduction;
		this.description = description;
		this.collaborator = collaborator;
		this.archive = archive;
	}

	public Knowledge() {
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

	public String getTitleImag() {return titleImag;}
	public void setTitleImag(String titleImag) {this.titleImag = titleImag;}

	public String getImag() {return imag;}
	public void setImag(String imag) {this.imag = imag;}

	public String getIntroduction() {return introduction;}
	public void setIntroduction(String introduction) {this.introduction = introduction;}

	public String getDescription() {return description;}
	public void setDescription(String description) { this.description = description;}

	public String getCollaborator() {return collaborator;}
	public void setCollaborator(String collaborator) {this.collaborator = collaborator;}

	public String getArchive() {
		return archive;
	}
	public void setArchive(String archive) {
		this.archive = archive;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isActive() {return active;}
	
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
		Knowledge other = (Knowledge) obj;
		return Objects.equals(id, other.id);
	}

}
