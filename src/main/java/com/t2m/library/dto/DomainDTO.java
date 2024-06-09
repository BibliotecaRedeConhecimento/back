package com.t2m.library.dto;

import com.t2m.library.entities.Domain;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class DomainDTO {


    private Long id;
    @NotBlank(message = "Campo requerido")
    private String name;
    private boolean active;

    public DomainDTO() {

    }
    public DomainDTO(Long Id, String name){

        this.name =  name;
    }
    public DomainDTO(Domain entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.active = entity.isActive();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public boolean isActive() {return active;}

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
