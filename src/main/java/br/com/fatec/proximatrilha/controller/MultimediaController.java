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

import br.com.fatec.proximatrilha.model.Multimedia;
import br.com.fatec.proximatrilha.service.MultimediaService;

@RestController
@RequestMapping(value="/trails/{trailId}/traildots/{trailDotId}/multimedias")
public class MultimediaController {
	
	@Autowired
	private MultimediaService multimediaService;

	public void setMultimediaService(final MultimediaService multimediaService) {
		this.multimediaService = multimediaService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Multimedia>> getAll(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId) {
		return new ResponseEntity<Collection<Multimedia>>(multimediaService.getAll(trailId, trailDotId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Multimedia> createMultimedia(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId, @RequestBody final Multimedia multimedia) {
		return new ResponseEntity<Multimedia>(multimediaService.create(trailId, trailDotId, multimedia), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{multimediaId}", method = RequestMethod.GET)
	public ResponseEntity<Multimedia> getById(@PathVariable("multimediaId") final Long multimediaId) {
		return new ResponseEntity<Multimedia>(multimediaService.getById(multimediaId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{multimediaId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Multimedia> updateMultimedia(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId, @PathVariable("multimediaId") final Long multimediaId, @RequestBody final Multimedia multimedia) {
		return new ResponseEntity<Multimedia>(multimediaService.update(trailId, trailDotId, multimediaId, multimedia), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{multimediaId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteMultimedia(@PathVariable("trailId") final Long trailId, @PathVariable("trailDotId") final Long trailDotId, @PathVariable("multimediaId") final Long multimediaId) {
		multimediaService.delete(trailId, trailDotId, multimediaId);
	}
	
}