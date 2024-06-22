package com.t2m.library.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_domain")
public class Domain {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;
    private Boolean active;
    
    @ManyToMany(mappedBy = "domains", cascade = CascadeType.ALL)
	private Set<Category> categories = new HashSet<>();
    
    @ManyToMany
	@JoinTable(name = "tb_domain_user", joinColumns = @JoinColumn(name = "domain_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

    public Domain() {
    	this.active = true;
    }
    public Domain(Long Id, String name){
        this.name =  name;
        this.active = true;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() {return active;}

    public boolean hasCategories() {
        return !categories.isEmpty();
    }

    @PreUpdate
    private void preRemove() {
        if (hasCategories()) {
            throw new IllegalStateException("Não é possível inativar este dominio porque existem categorias associadas a ele");
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Domain other = (Domain) obj;
        return Objects.equals(id, other.id);
    }

}
