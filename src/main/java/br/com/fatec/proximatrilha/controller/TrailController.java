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

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.service.TrailService;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.view.View;

@RestController
@RequestMapping({"/trails", "/"})
public class TrailController {
	
	@Autowired
	private TrailService trailService;
	
	@Autowired
	private UserService userService;
	
	
	public void setTrailService(TrailService trailService) {
		this.trailService = trailService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Trail>> getAll() {
		return new ResponseEntity<Collection<Trail>>(trailService.getAll(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(View.General.class)
	public ResponseEntity<Trail> createTrail(@RequestBody final Trail trail) {
		Trail trailCreated = trailService.create(trail);
		userService.assignTrails(trailCreated);
		return new ResponseEntity<Trail>(trailCreated, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{trailId}", method = RequestMethod.GET)
	public ResponseEntity<Trail> getById(@PathVariable("trailId") final Long trailId) {
		return new ResponseEntity<Trail>(trailService.getById(trailId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{trailId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(View.Trail.class)
	public ResponseEntity<Trail> updateTrail(@PathVariable("trailId") final Long trailId, @RequestBody final Trail trail) {
		return new ResponseEntity<Trail>(trailService.update(trailId, trail), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{trailId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteTrail(@PathVariable("trailId") final Long trailId) {
		trailService.delete(trailId);
	}
}