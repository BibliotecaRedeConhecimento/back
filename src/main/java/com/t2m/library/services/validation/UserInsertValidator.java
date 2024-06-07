package com.t2m.library.services.validation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.t2m.library.controllers.exceptions.MissingAuthorizationException;
import com.t2m.library.dto.RoleDTO;
import com.t2m.library.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;

import com.t2m.library.controllers.exceptions.FieldMessage;
import com.t2m.library.dto.UserInsertDTO;
import com.t2m.library.entities.User;
import com.t2m.library.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

	private final Role ROLE_ADMIN = new Role(2L, "ROLE_ADMIN");

	@Autowired
	private UserRepository repository;

	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		boolean canCreateAdmin = (currentAuthentication.isAuthenticated() &&
				!currentAuthentication.getAuthorities().contains(new Role(0L, "ROLE_ANONYMOUS")) &&
				currentAuthentication.getAuthorities().contains(ROLE_ADMIN)) ||
				repository.findAll().isEmpty();

		if (!canCreateAdmin && dto.getRoles().contains(new RoleDTO(ROLE_ADMIN)) ) {
			throw new MissingAuthorizationException("Cannot create admin user without permission");
		}

		User user = repository.findByEmail(dto.getEmail());
		if (user != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
