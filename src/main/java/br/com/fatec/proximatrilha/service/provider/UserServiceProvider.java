package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.repository.UserRepository;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.UserValidatorService;

@Service("userService")
@Transactional
public class UserServiceProvider implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidatorService userValidatorService;
	
	public void setUserRepository(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setUserValidatorService(UserValidatorService userValidatorService) {
		this.userValidatorService = userValidatorService;
	}

	public Collection<User> getAll() {
		List<User> users = new ArrayList<User>();
		for (User user: userRepository.findAll()) {
			users.add(user);
		}
		return Collections.unmodifiableList(users);
	}

	public User create(final User user) {
		userValidatorService.validateCreateUser(user);
		return userRepository.save(user);
	}

	public User getById(final Long userId) {
		return userRepository.findOne(userId);
	}

	public User update(final Long userId, final User user) {
		userValidatorService.validateUpdateUser(userId, user);
		return userRepository.save(user);
	}

	public void delete(Long userId) {
		notNullParameter(userId, "userId");
		if (!userRepository.exists(userId)) {
			throw new IllegalArgumentException("Id não encontrado.");
		}
		else {
			userRepository.delete(userId);
		}
	}

}