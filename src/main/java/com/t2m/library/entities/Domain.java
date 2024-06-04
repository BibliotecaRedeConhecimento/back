package com.t2m.library.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_domain")
public class Domain {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Domain() {}
    public Domain(Long Id, String name){
        this.name =  name;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

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
