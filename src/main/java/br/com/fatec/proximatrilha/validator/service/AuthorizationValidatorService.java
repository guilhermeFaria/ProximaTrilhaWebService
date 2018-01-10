package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.Authorization;

public interface AuthorizationValidatorService {

	public void validateCreateAuthorization(final Authorization authorization);

	public void validateUpdateAuthorization(final Long authorizationId, final Authorization authorization);

}
