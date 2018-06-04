package br.com.fatec.proximatrilha.validator.service;

import java.util.Set;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.model.User;

public interface UserValidatorService {

	public void validateCreateUser(final User user);

	public void validateUpdateUser(final Long userId, final User user);

	public void validateDeleteUser(final Long userId);
	
	public Boolean validateExistUser(final Long userId);

	public void validateAssignRole(final Long userId, final Set<Authorization> authoritys);
	
}
