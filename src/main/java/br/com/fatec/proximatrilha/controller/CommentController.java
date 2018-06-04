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

import br.com.fatec.proximatrilha.model.Comment;
import br.com.fatec.proximatrilha.service.CommentService;

@RestController
@RequestMapping("/trails/{trailId}/traildots/{trailDotId}/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Comment>> getAll(@PathVariable("trailId")final Long trailId, 
			@PathVariable("trailDotId")final Long trailDotId) {
		return new ResponseEntity<Collection<Comment>>(commentService.getAll(trailId, trailDotId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comment> createComment(@PathVariable("trailId")final Long trailId, 
			@PathVariable("trailDotId")final Long trailDotId, @RequestBody final Comment comment) {
		return new ResponseEntity<Comment>(commentService.create(trailId, trailDotId, comment), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
	public ResponseEntity<Comment> getById(@PathVariable("commentId") final Long commentId) {
		return new ResponseEntity<Comment>(commentService.getById(commentId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{commentId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comment> updateComment(@PathVariable("trailId")final Long trailId, 
			@PathVariable("trailDotId")final Long trailDotId, @PathVariable("commentId") final Long commentId,
			@RequestBody final Comment comment) {
		return new ResponseEntity<Comment>(commentService.update(trailId, trailDotId, commentId, comment), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{commentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteComment(@PathVariable("trailId")final Long trailId, 
			@PathVariable("trailDotId")final Long trailDotId, @PathVariable("commentId") final Long commentId) {
		commentService.delete(trailId, trailDotId, commentId);
	}
}