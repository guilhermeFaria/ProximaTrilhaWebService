package br.com.fatec.proximatrilha.service;

import java.util.Collection;

import br.com.fatec.proximatrilha.model.Comment;

public interface CommentService {

	public Collection<Comment> getAll(final Long trailId, final Long trailDotId);

	public Comment getById(final Long commentId);
	
	public Comment create(final Long trailId, final Long trailDotId, Comment comment);

	public Comment update(final Long trailId, final Long trailDotId, final Long commentId, final Comment comment);

	public void delete(final Long trailId, final Long trailDotId, final Long commentId );
}