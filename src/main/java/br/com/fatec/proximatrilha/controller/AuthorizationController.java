package br.com.fatec.proximatrilha.controller;

import java.util.Collection;

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

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.service.AuthorizationService;

@RestController
@RequestMapping("/authorizations")
public class AuthorizationController {

	@Autowired
	private AuthorizationService authorizationService;

	public void setAuthorizationService(final AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Authorization>> getAll() {
		return new ResponseEntity<Collection<Authorization>>(authorizationService.getAll(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/searchbyname/{authority}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Authorization>> searchAuthority(@PathVariable("authority") final String authority) {
		return new ResponseEntity<Collection<Authorization>>(authorizationService.searchAuthority(authority), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Authorization> createAuthorization(@RequestBody final Authorization authorization) {
		return new ResponseEntity<Authorization>(authorizationService.create(authorization), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{authorizationId}", method = RequestMethod.GET)
	public ResponseEntity<Authorization> getById(@PathVariable("authorizationId") final Long authorizationId) {
		return new ResponseEntity<Authorization>(authorizationService.getById(authorizationId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{authorizationId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Authorization> updateAuthorization(@PathVariable("authorizationId") final Long authorizationId, @RequestBody final Authorization authorization) {
		return new ResponseEntity<Authorization>(authorizationService.update(authorizationId, authorization), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{authorizationId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteAuthorization(@PathVariable("authorizationId") final Long authorizationId) {
		authorizationService.delete(authorizationId);
	}
	
}