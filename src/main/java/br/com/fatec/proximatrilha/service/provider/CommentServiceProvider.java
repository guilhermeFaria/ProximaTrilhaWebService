package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Comment;
import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.repository.CommentRepository;
import br.com.fatec.proximatrilha.service.CommentService;
import br.com.fatec.proximatrilha.service.TrailDotService;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.CommentValidatorService;

@Service("commentService")
@Transactional
public class CommentServiceProvider implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentValidatorService commentValidatorService;
	
	@Autowired
	private TrailDotService trailDotService;
	
	@Autowired
	private UserService userService;
	
	public void setTrailDotService(TrailDotService trailDotService) {
		this.trailDotService = trailDotService;
	}

	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public void setCommentValidatorService(CommentValidatorService commentValidatorService) {
		this.commentValidatorService = commentValidatorService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public static Logger logger = Logger.getLogger("trail");
	
	@Override
	public Collection<Comment> getAll(final Long trailId, final Long trailDotId) {
		Set<Comment> comments  = new HashSet<Comment>();
		for (Comment comment: commentRepository.findAll()) {
			comments.add(comment);
		}
		return comments;
	}

	@Override
	public Comment create(final Long trailId, final Long trailDotId, final Comment comment) {
		Comment commentCreated = null;
		commentValidatorService.validateCreateComment(comment);
		TrailDot trailDot = trailDotService.getById(trailId, trailDotId);
		try {
			comment.setInsertDate(new Timestamp(System.currentTimeMillis()));
			comment.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			comment.setUserId(userService.getUserFromSession().getId());
			comment.setTrailDot(trailDot);
			commentCreated = commentRepository.save(comment);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Erro ao criar um novo comentario." +ex.getStackTrace());
		}
		return commentCreated;
	}

	@Override
	public Comment getById(final Long commentId) {
		return commentRepository.findOne(commentId);
	}

	@Override
	public Comment update(final Long trailId, final Long trailDotId, final Long commentId, final Comment comment) {
		Comment commentUpdated = null;
		Comment commentOld = getById(commentId);
		commentValidatorService.validateUpdateComment(commentOld.getUserId(), trailId, trailDotId, commentId, comment);
		try {
			commentOld.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			commentOld.setMessage(comment.getMessage());
			commentUpdated = commentRepository.save(commentOld);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao atualizar o comentario" +ex.getStackTrace());
		}
		return commentUpdated;
	}

	@Override
	public void delete(final Long trailId, final Long trailDotId, final Long commentId) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		notNullParameter(commentId, "commentId");
		commentValidatorService.existComment(commentId);
		try {
			Comment comment = getById(commentId);
			userService.validateUserAction(comment.getUserId());
			commentRepository.delete(commentId);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao remover o comentario." + ex.getStackTrace());
		}
	}
	
}