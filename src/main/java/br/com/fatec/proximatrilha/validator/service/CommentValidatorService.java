package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.Comment;

public interface CommentValidatorService {

	public void validateCreateComment(final Comment comment);

	public void validateUpdateComment(final Long userId, final Long trailId, final Long trailDotId, final Long commentId, final Comment comment);

	public Boolean existComment(final Long commentId);

}
