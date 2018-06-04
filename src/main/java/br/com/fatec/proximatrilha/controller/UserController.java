package br.com.fatec.proximatrilha.controller;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.view.View;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	@JsonView(View.General.class)
	public ResponseEntity<Collection<User>> getAll() {
		return new ResponseEntity<Collection<User>>(userService.getAll(), HttpStatus.OK);
	}
	
	@JsonView(View.Individual.class)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody final User user) {
		return new ResponseEntity<User>(userService.create(user), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@JsonView(View.Individual.class)
	@RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET)
	public ResponseEntity<Collection<Authorization>> getRoles(@PathVariable("userId") final Long userId) {
		return new ResponseEntity<Collection<Authorization>>(userService.getRoles(userId), HttpStatus.OK);
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@JsonView(View.Individual.class)
	@RequestMapping(value = "/{userId}/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Authorization>> assignRole(@PathVariable("userId") final Long userId, @RequestBody final Set<Authorization> authoritys) {
		return new ResponseEntity<Collection<Authorization>>(userService.assignRole(userId, authoritys), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{userId}/roles", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeRole(@PathVariable("userId") final Long userId, @RequestBody final Set<Authorization> authoritys) {
		userService.removeRole(userId, authoritys);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@JsonView(View.Individual.class)
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getById(@PathVariable("userId") final Long userId) {
		return new ResponseEntity<User>(userService.getById(userId), HttpStatus.OK);
	}
	
	@JsonView(View.Individual.class)
	@RequestMapping(value = "/{userId}/trails", method = RequestMethod.GET)
	public ResponseEntity<Collection<Trail>> getTrailsByUserId(@PathVariable("userId") final Long userId) {
		return new ResponseEntity<Collection<Trail>>(userService.getTrailsByUserId(userId), HttpStatus.OK);
	}
	
	@JsonView(View.Individual.class)
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<User> updateUser(@PathVariable("userId") final Long userId, @RequestBody final User user) {
		return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("userId") final Long userId) {
		userService.delete(userId);
	}

}