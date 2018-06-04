package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.repository.UserRepository;
import br.com.fatec.proximatrilha.service.AuthorizationService;
import br.com.fatec.proximatrilha.validator.service.UserValidatorService;
@Service("userValidatorService")
@Transactional
public class UserValidatorServiceProvider implements UserValidatorService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void validateCreateUser(final User user) {
		notNullParameter(user.getName(), "name");
		notNullParameter(user.getEmail(), "email");
		notNullParameter(user.getPassword(), "password");
		
		if(!(userRepository.findByEmail(user.getEmail()) == null)) {
			throw new IllegalArgumentException("O e-mail informado j&aacute; est&aacute; sendo utilizado.");
		}
		
		
	}

	public void validateUpdateUser(final Long userId, final User user) {
		notNullParameter(userId, "userId");
		notNullParameter(user.getId(), "id");
		if (!userId.equals(user.getId())) {
			throw new IllegalArgumentException("Ha uma divergencia entre os IDs informado!");
		}
		validateExistUser(userId);
		notNullParameter(user.getName(), "name");
		notNullParameter(user.getEmail(), "email");
		
	}

	@Override
	public void validateDeleteUser(Long userId) {
		notNullParameter(userId, "userId");
		validateExistUser(userId);
	}
	
	public Boolean validateExistUser(final Long userId) {
		if (!userRepository.exists(userId)) {
			throw new IllegalArgumentException("Id n&atilde;o encontrado.");
		}
		else {
			return Boolean.TRUE;
		}
		
	}

	@Override
	public void validateAssignRole(final Long userId, final Set<Authorization> authoritys) {
		notNullParameter(userId, "userId");
		notNullParameter(authoritys, "authoritys");
		validateExistUser(userId);
		authorizationService.existAuthority(authoritys);
		
	}
}