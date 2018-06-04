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

import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.service.TrailDotService;

@RestController
@RequestMapping(value="/trails/{trailId}/traildots")
public class TrailDotController {
	
	@Autowired
	private TrailDotService trailDotService;

	public void setTrailDotService(TrailDotService trailDotService) {
		this.trailDotService = trailDotService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<TrailDot>> getAll(@PathVariable("trailId") final Long trailId) {
		return new ResponseEntity<Collection<TrailDot>>(trailDotService.getAll(trailId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{trailDotId}", method = RequestMethod.GET)
//	@JsonView(View.Trail.class)
	public ResponseEntity<TrailDot> getById(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId) {
		return new ResponseEntity<TrailDot>(trailDotService.getById(trailId, trailDotId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@JsonView(View.TrailDot.class)
	public ResponseEntity<TrailDot> createTrailDot(@PathVariable("trailId") final Long trailId, @RequestBody final TrailDot trailDot) {
		return new ResponseEntity<TrailDot>(trailDotService.create(trailId, trailDot), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{trailDotId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrailDot> updateTrailDot(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId, @RequestBody final TrailDot trailDot) {
		return new ResponseEntity<TrailDot>(trailDotService.update(trailId, trailDotId, trailDot), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{trailDotId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteTrailDot(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId) {
		trailDotService.delete(trailId, trailDotId);
	}
}