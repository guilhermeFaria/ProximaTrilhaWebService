package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.validator.service.AuthorizationValidatorService;

@Service("authorizationValidatorService")
@Transactional
public class AuthorizationValidatorServiceProvider implements AuthorizationValidatorService {

	public void validateCreateAuthorization(final Authorization authorization) {
		notNullParameter(authorization.getAuthority(), "authority");		
	}

	public void validateUpdateAuthorization(final Long authorizationId, final Authorization authorization) {
		notNullParameter(authorizationId, "id");
		notNullParameter(authorization.getId(), "id");
		if (!authorizationId.equals(authorization.getId())) {
			throw new IllegalArgumentException();
		}
		notNullParameter(authorization.getAuthority(), "authority");
	}

}