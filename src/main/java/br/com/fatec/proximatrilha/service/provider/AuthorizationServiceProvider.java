package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.isNotEmpty;
import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.repository.AuthorizationRepository;
import br.com.fatec.proximatrilha.service.AuthorizationService;
import br.com.fatec.proximatrilha.validator.service.AuthorizationValidatorService;

@Service("authorizationService")
@Transactional
public class AuthorizationServiceProvider implements AuthorizationService {

	@Autowired
	private AuthorizationRepository authorizationRepository;
	
	@Autowired
	private AuthorizationValidatorService authorizationValidatorService;
	
	public void setAuthorizationRepository(AuthorizationRepository authorizationRepository) {
		this.authorizationRepository = authorizationRepository;
	}

	public Collection<Authorization> getAll() {
		List<Authorization> authorizations  = new ArrayList<Authorization>();
		for (Authorization authorization: authorizationRepository.findAll()) {
			authorizations.add(authorization);
		}
		return authorizations;
	}

	public Authorization create(Authorization authorization) {
		authorizationValidatorService.validateCreateAuthorization(authorization);
		
		return authorizationRepository.save(authorization);
	}

	public Authorization getById(final Long authorizationId) {
		return authorizationRepository.findOne(authorizationId);
	}

	public Authorization update(final Long authorizationId, final Authorization authorization) {
		authorizationValidatorService.validateUpdateAuthorization(authorizationId, authorization);
		return authorizationRepository.save(authorization);
	}

	public List<Authorization> getByName(final String name) {
		isNotEmpty(name, "name" );	
		return authorizationRepository.findByNameContainsIgnoreCase(name);
	}

	public void delete(Long authorizationId) {
		notNullParameter(authorizationId, "authorizationId");
		if (!authorizationRepository.exists(authorizationId)) {
			throw new IllegalArgumentException("Id não encontrado.");
		}
		else {
			authorizationRepository.delete(authorizationId);
		}
	}

}
