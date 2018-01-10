package br.com.fatec.proximatrilha.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getAll() {
		return new ResponseEntity<Collection<User>>(userService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody final User user) {
		return new ResponseEntity<User>(userService.create(user), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getById(@PathVariable("userId") final Long userId) {
		return new ResponseEntity<User>(userService.getById(userId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable final Long userId, @RequestBody final User user) {
		return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable final Long userId) {
		userService.delete(userId);
	}

}
