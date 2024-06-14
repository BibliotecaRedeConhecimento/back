package com.t2m.library.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_domain")
@SQLDelete(sql = "UPDATE tb_domain SET active = false WHERE id=?")
@FilterDef(name = "activeDomainFilter", parameters = @ParamDef(name = "isActive", type = boolean.class))
@Filter(name = "activeDomainFilter", condition = "active = :isActive")
public class Domain {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;
    private boolean active = true;
    
    @ManyToMany(mappedBy = "domains")
	private Set<Category> categories = new HashSet<>();
    
    @ManyToMany
	@JoinTable(name = "tb_domain_user", joinColumns = @JoinColumn(name = "domain_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

    public Domain() {}
    public Domain(Long Id, String name){
        this.name =  name;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

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
        if (obj == null || getClass() != obj.getClass())
            return false;
        Domain other = (Domain) obj;
        return Objects.equals(id, other.id);
    }

}
