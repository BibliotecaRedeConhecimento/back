package com.t2m.library.dto;

import com.t2m.library.entities.Domain;


import java.util.Objects;

public class DomainDTO {


    private Long id;
    private String name;

    public DomainDTO() {

    }
    public DomainDTO(Long Id, String name){

        this.name =  name;
    }
    public DomainDTO(Domain entity){

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
        DomainDTO other = (DomainDTO) obj;
        return Objects.equals(id, other.id);
    }
}
