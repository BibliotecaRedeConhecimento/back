package com.t2m.library.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
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
@Table(name = "tb_category")
@SQLDelete(sql = "UPDATE tb_category SET active = false WHERE id=?")
@FilterDef(name = "activeCategoryFilter", parameters = @ParamDef(name = "isActive", type = boolean.class))
@Filter(name = "activeCategoryFilter", condition = "active = :isActive")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Boolean active;
	
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Knowledge> knowledges = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "tb_category_domain", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "domain_id"))
	private Set<Domain> domains = new HashSet<>();
	
	public Category() {
		this.active = true;
	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
		this.active = true;
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

	public Set<Domain> getDomains() {
		return domains;
	}

	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

}
