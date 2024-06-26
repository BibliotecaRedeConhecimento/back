package com.t2m.library.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.t2m.library.projections.IdProjection;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tb_knowledge")
@SQLDelete(sql = "UPDATE tb_knowledge SET active = false WHERE id=?")
public class Knowledge implements IdProjection<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String title;
	private String titleMedia;
	
	@Column(columnDefinition = "TEXT")
	private String introduction;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	private String collaborator;
	private String archive;
	private Boolean active;
	private boolean needsReview;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "tb_knowledge_category", joinColumns = @JoinColumn(name = "knowledge_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	public Knowledge(Long id, String title, String titleMedia,String imag, String introduction, String description, String collaborator, String archive, Boolean active, Boolean needsReview) {
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

	public Knowledge() {
		this.active = true;
	}

	@Override
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

	public Boolean getNeedsReview() {return needsReview;}
	public void setNeedsReview(boolean needsReview) {this.needsReview = needsReview;}


	public Set<Category> getCategories() {
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
		Knowledge other = (Knowledge) obj;
		return Objects.equals(id, other.id);
	}

}
