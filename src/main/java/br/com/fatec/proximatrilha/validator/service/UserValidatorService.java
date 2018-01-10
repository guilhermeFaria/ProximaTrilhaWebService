package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.User;

public interface UserValidatorService {

	public void validateCreateUser(final User user);

	public void validateUpdateUser(final Long userId, final User user);

}
