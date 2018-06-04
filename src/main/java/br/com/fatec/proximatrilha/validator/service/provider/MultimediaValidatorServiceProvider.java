package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Multimedia;
import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.repository.MultimediaRepository;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.MultimediaValidatorService;
import br.com.fatec.proximatrilha.validator.service.TrailDotValidatorService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;

@Service("multimediaValidatorService")
@Transactional
public class MultimediaValidatorServiceProvider implements MultimediaValidatorService {
	
	@Autowired
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	private TrailValidatorService trailValidatorService;
	
	@Autowired
	private TrailDotValidatorService trailDotValidatorService;
	
	@Autowired
	private UserService userService;
		
	public void setTrailDotValidatorService(TrailDotValidatorService trailDotValidatorService) {
		this.trailDotValidatorService = trailDotValidatorService;
	}

	public void setTrailValidatorService(TrailValidatorService trailValidatorService) {
		this.trailValidatorService = trailValidatorService;
	}

	public void setMultimediaRepository(MultimediaRepository multimediaRepository) {
		this.multimediaRepository = multimediaRepository;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void validateCreateMultimedia(final Long trailId, final Long trailDotId, final TrailDot trailDot, final Multimedia multimedia) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		trailValidatorService.existTrail(trailId);
		trailDotValidatorService.existTrailDot(trailDotId);
		notNullParameter(multimedia.getUrl(), "url");
		userService.validateUserAction(trailDot.getTrail().getUser().getId());
	}

	public void validateUpdateTrail(final Long trailId, final Long trailDotId, final TrailDot trailDot, final Long multimediaId, final Multimedia multimedia) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		trailValidatorService.existTrail(trailId);
		trailDotValidatorService.existTrailDot(trailDotId);
		notNullParameter(multimedia.getId(), "id");
		notNullParameter(multimedia.getUrl(), "url");
		notNullParameter(multimedia.getInsertDate(), "insertDate");
		userService.validateUserAction(trailDot.getTrail().getUser().getId());
	}
	
	@Override
	public Boolean existMultimedia(final Long multimediaId) {
		if(!multimediaRepository.exists(multimediaId)) {
			throw new IllegalArgumentException("Id n&atilde;o encontrado.");
		}

		return Boolean.TRUE;
		
	}

	@Override
	public void validateDeleteMultimedia(final Multimedia multimedia) {
		userService.validateUserAction(multimedia.getTrailDot().getTrail().getUser().getId());
	}
	
}
