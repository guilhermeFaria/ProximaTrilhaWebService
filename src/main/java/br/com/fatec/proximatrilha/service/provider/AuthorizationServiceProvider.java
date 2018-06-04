package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.isNotEmpty;
import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	public static Logger logger = Logger.getLogger("authorization");
	
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
		Authorization authorityCreated = null;
		try {
			authorityCreated = authorizationRepository.save(authorization);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao criar uma nova role. " + ex.getStackTrace());
		}
		
		return authorityCreated;
	}

	public Authorization getById(final Long authorizationId) {
		return authorizationRepository.findOne(authorizationId);
	}

	public Authorization update(final Long authorizationId, final Authorization authorization) {
		Authorization authorizationUpdated = null;
		try {
			authorizationValidatorService.validateUpdateAuthorization(authorizationId, authorization);
			authorizationUpdated = authorizationRepository.save(authorization);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao atualizar as informações da role de acesso." + ex.getStackTrace());
		}
		
		return authorizationUpdated;
	}

	public List<Authorization> getByAuthority(final String authority) {
		isNotEmpty(authority, "authority" );	
		return authorizationRepository.findByAuthorityContainsIgnoreCase(authority);
	}

	public void delete(final Long authorizationId) {
		try {
			notNullParameter(authorizationId, "authorizationId");
			if (!authorizationRepository.exists(authorizationId)) {
				throw new IllegalArgumentException("Id n&atilde;o encontrado.");
			}
			else {
				authorizationRepository.delete(authorizationId);
			}
		}catch(Exception ex){
			logger.log(Level.SEVERE, "Ocorreu erro ao remover a role de acesso." +ex.getStackTrace());
		}
		
	}
	
	public Boolean existAuthority(final Set<Authorization> authorizations) {
		
		for (Authorization authorization: authorizations) {
			if(!(authorization.getId() == null)) {
				getById(authorization.getId());
			}
			else if(authorizationRepository.findByAuthorityContainsIgnoreCase(authorization.getAuthority()).isEmpty()) {
				throw new IllegalArgumentException("Id n&atilde;o encontrado.");
			}
		}
		
		return Boolean.TRUE;
	}

	@Override
	public Collection<Authorization> searchAuthority(final String authority) {
		return authorizationRepository.findByAuthorityContainsIgnoreCase(authority);
	}

}