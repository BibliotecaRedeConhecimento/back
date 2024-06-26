package com.t2m.library.dto;

import com.t2m.library.entities.Role;

import java.util.Objects;

public class RoleDTO {

	private Long id;
	private String authority;
	
	public RoleDTO() {
	}

	public RoleDTO(Long id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}

	public RoleDTO(Role role) {
		super();
		id = role.getId();
		authority = role.getAuthority();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoleDTO roleDTO = (RoleDTO) o;
		return Objects.equals(this.getId(), roleDTO.getId());
	}
}
