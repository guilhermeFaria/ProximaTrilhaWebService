package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Comment;
import br.com.fatec.proximatrilha.repository.CommentRepository;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.CommentValidatorService;

@Service("commentValidatorService")
@Transactional
public class CommentValidatorServiceProvider implements CommentValidatorService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
			
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void validateCreateComment(final Comment comment) {
		notNullParameter(comment.getMessage(), "message");
	}
	
	public void validateUpdateComment(final Long userId, final Long trailId, final Long trailDotId, final Long commentId, final Comment comment) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		notNullParameter(commentId, "commentId");
		notNullParameter(comment.getId(), "id");
		if (!commentId.equals(comment.getId())) {
			throw new IllegalArgumentException();
		}
		notNullParameter(comment.getMessage(), "message");
		
		userService.validateUserAction(userId);
	}
	
	@Override
	public Boolean existComment(final Long commentId) {
		if(!commentRepository.exists(commentId)) {
			throw new IllegalArgumentException("Id n&atilde;o encontrado.");
		}

		return Boolean.TRUE;
		
	}
	
}