package com.t2m.library.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.t2m.library.entities.Domain;

import jakarta.validation.constraints.NotBlank;

public class DomainDTO {

    private Long id;
    @NotBlank(message = "Campo requerido")
    private String name;
    
    private Boolean active;
    
    private List<CategoryDTO> categories = new ArrayList<>();

    public DomainDTO() {

    }
    public DomainDTO(Long Id, String name){

        this.name =  name;
    }
    public DomainDTO(Domain entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.active = entity.getActive();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Boolean isActive() {return active;}
    public void setActive(Boolean active) {this.active = active;}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
    
    public static class SuccessMenssageDTO{
        private String message = "Operação realizada com sucesso!";

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
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
        DomainDTO other = (DomainDTO) obj;
        return Objects.equals(id, other.id);
    }

}
