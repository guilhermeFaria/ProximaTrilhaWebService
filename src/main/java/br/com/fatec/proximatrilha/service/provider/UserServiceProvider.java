package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.repository.AuthorizationRepository;
import br.com.fatec.proximatrilha.repository.UserRepository;
import br.com.fatec.proximatrilha.service.AuthorizationService;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;
import br.com.fatec.proximatrilha.validator.service.UserValidatorService;

@Service("userService")
@Transactional
public class UserServiceProvider implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidatorService userValidatorService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private AuthorizationRepository authorizationRepository;
	
	@Autowired
	private TrailValidatorService trailValidatorService;
	
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setTrailValidatorService(TrailValidatorService trailValidatorService) {
		this.trailValidatorService = trailValidatorService;
	}

	public void setUserRepository(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setUserValidatorService(UserValidatorService userValidatorService) {
		this.userValidatorService = userValidatorService;
	}

	public static Logger logger = Logger.getLogger("user");
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public Collection<User> getAll() {
		logger.log(Level.INFO, "Iniciando  as buscas.");
		List<User> users = new ArrayList<User>();
		try {
			for (User user: userRepository.findAll()) {
				users.add(user);
			}
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao buscar todos os usuarios: " + ex.getStackTrace());
		}
		
		return Collections.unmodifiableList(users);
	}

	public User create(final User user) {
		userValidatorService.validateCreateUser(user);
		User userCreated = null;
		try {
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			user.setPassword(md5.encodePassword(user.getPassword(), null));
			userCreated = userRepository.save(user);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao criar um novo usuario." + ex.getStackTrace());
		}
		
		return userCreated; 
	}

	public User getById(final Long userId) {
		User user = new User();
		try {
		user = userRepository.findOne(userId);
		
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao retornar o usuario por id." + ex.getStackTrace());
		}
		return user;
	}

	public User update(final Long userId, final User user) {
		userValidatorService.validateUpdateUser(userId, user);
		User userUpdated = null;
		try {
			userUpdated = userRepository.save(user);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao criar um novo usuario." + ex.getStackTrace());
		}
		return userUpdated;
	}

	public void delete(Long userId) {
		userValidatorService.validateDeleteUser(userId);
		User user = getById(userId);
		validateUserAction(user.getId());
		try {
			removeRole(userId, user.getAuthorizations());
			for(Trail trail: user.getTrails()) {
				removeTrail(user, trail);
			}
			userRepository.delete(userId);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao criar um novo usuario." + ex.getStackTrace());
		}
		
	}

	@Override
	public Collection<Authorization> assignRole(final Long userId, Set<Authorization> authoritys) {
		notNullParameter(userId, "userId");
		Authorization authorityRoleAdmin = authorizationRepository.findTop1ByAuthorityContainsIgnoreCase(ROLE_ADMIN);
		userValidatorService.validateAssignRole(userId, authoritys);
		
		if(authoritys.contains(authorityRoleAdmin)) {
			User userSession = getUserFromSession();
			if(!userSession.getAuthorizations().contains(authorityRoleAdmin)) {
				throw new IllegalArgumentException("Necessario perfil de administrador para realizar este processo.");
			}
		}
		User user = getById(userId);
		try {
			Set<Authorization> authorizations =  user.getAuthorizations();
			authorizations.addAll(authoritys);
			user.setAuthorizations(authorizations);
			user = userRepository.save(user);
		}catch(IllegalArgumentException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new IllegalArgumentException(ex.getMessage());
		}
		catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao associar uma Role ao usuario");
		}
		
		return user.getAuthorizations();
	}
	
	@Override
	public User removeRole(Long userId, Set<Authorization> authoritys) {
		notNullParameter(userId, "userId");
		notNullParameter(authoritys, "authoritys");
		
		User user;
		Set<Authorization> authorizations;
		
		userValidatorService.validateExistUser(userId);
		authorizationService.existAuthority(authoritys);
		user = getById(userId);
		validateUserAction(user.getId());
		try {
			authorizations = user.getAuthorizations();
			authorizations.removeAll(authoritys);
			
			user.setAuthorizations(authorizations);
			userRepository.save(user);
			
		}catch (Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao remover a(s) role(s) do usuario." + ex.getStackTrace());
		}
		return user;
	}

	@Override
	public User assignTrails(final Trail trail) {
		User user = getUserFromSession();
		Set<Trail> trails;
		trails = user.getTrails();
		trailValidatorService.existTrail(trail.getId());
		try {
			trails.add(trail);
			user.setTrails(trails);
			userRepository.save(user);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao associar uma trilha ao usuario." + ex.getStackTrace());
		}
		return user;
	}
	
	@Override
	public User removeTrail(final User user, final Trail trail) {
		try {
			Set<Trail> trails = user.getTrails();
			trails.remove(trail);
			user.setTrails(trails);
			userRepository.save(user);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro na remo&ccedil;&atilde;o da associa&ccedil;&atilde;o da trilha com o usu&aacute;rio.\r\n " 
					+ ex.getStackTrace());
		}
		return user;
	}

	@Override
	public Collection<Authorization> getRoles(final Long userId) {
		notNullParameter(userId, "userId");
		User user;
		userValidatorService.validateExistUser(userId);
		user = getById(userId);
		return user.getAuthorizations();
	}

	@Override
	public Collection<Trail> getTrailsByUserId(final Long userId) {
		User user = getById(userId);
		return user.getTrails();
	}
	
	public User getUserFromSession() {
		final String userEmail = (((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getName());
		return userRepository.findByEmail(userEmail);
	}
	
	@Override
	public void validateUserAction(final Long userId) {
		Authorization authorityRoleAdmin = 
				authorizationRepository.findTop1ByAuthorityContainsIgnoreCase(ROLE_ADMIN);
		
		if(!getUserFromSession().getId().equals(userId) && 
				!getUserFromSession().getAuthorizations().contains(authorityRoleAdmin)) {
			throw new IllegalArgumentException("Ação não permitida.");
		}
	}
	
}