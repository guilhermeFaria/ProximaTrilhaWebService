package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.validator.service.AuthorizationValidatorService;

public class AuthorizationValidatorServiceProvider implements AuthorizationValidatorService {

	public void validateCreateAuthorization(final Authorization authorization) {
		notNullParameter(authorization.getName(), "name");		
	}

	public void validateUpdateAuthorization(final Long authorizationId, final Authorization authorization) {
		notNullParameter(authorizationId, "id");
		notNullParameter(authorization.getId(), "id");
		if (!authorizationId.equals(authorization.getId())) {
			throw new IllegalArgumentException();
		}
		notNullParameter(authorization.getName(), "name");
	}

}
