package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.validator.service.UserValidatorService;

public class UserValidatorServiceProvider implements UserValidatorService {

	public void validateCreateUser(final User user) {
		notNullParameter(user.getName(), "name");
		notNullParameter(user.getEmail(), "email");
	}

	public void validateUpdateUser(final Long userId, final User user) {
		notNullParameter(userId, "userId");
		notNullParameter(user.getId(), "id");
		notNullParameter(user.getName(), "name");
		notNullParameter(user.getEmail(), "email");
		if (!userId.equals(user.getId())) {
			throw new IllegalArgumentException();
		}
	}
}