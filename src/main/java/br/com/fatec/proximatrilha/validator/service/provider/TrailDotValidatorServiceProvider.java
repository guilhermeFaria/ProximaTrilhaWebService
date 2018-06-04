package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.repository.TrailDotRepository;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.TrailDotValidatorService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;

@Service("trailDotValidatorService")
@Transactional
public class TrailDotValidatorServiceProvider implements TrailDotValidatorService {

	@Autowired
	private TrailValidatorService trailValidatorService;
		
	@Autowired
	private TrailDotRepository trailDotRepository;
	
	@Autowired
	private UserService userService;
	
	public void setTrailDotRepository(TrailDotRepository trailDotRepository) {
		this.trailDotRepository = trailDotRepository;
	}

	public void setTrailValidatorService(TrailValidatorService trailValidatorService) {
		this.trailValidatorService = trailValidatorService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public void validateCreateTrailDot(final Long userId, final Long trailId, final TrailDot trailDot) {
		notNullParameter(trailId, "trailId");
		trailValidatorService.existTrail(trailId);
		notNullParameter(trailDot.getName(), "name");
		notNullParameter(trailDot.getDescription(), "description");
		notNullParameter(trailDot.getLatitude(), "latitude");
		notNullParameter(trailDot.getLongitude(), "longitude");
		
		userService.validateUserAction(trailDot.getTrail().getUser().getId());
	}

	public void validateUpdateTrailDot(final Long userId, final Long trailId, final Long trailDotId,
			final TrailDot trailDot) {
		notNullParameter(trailId, "trailId");
		trailValidatorService.existTrail(trailId);
		notNullParameter(trailDotId, "trailDotId");
		notNullParameter(trailDot.getId(), "trailDotid");
		
		if (!trailDotId.equals(trailDot.getId())) {
			throw new IllegalArgumentException();
		}
		notNullParameter(trailDot.getName(), "name");
		notNullParameter(trailDot.getDescription(), "email");
		notNullParameter(trailDot.getLatitude(), "latitude");
		notNullParameter(trailDot.getLongitude(), "longitude");
		notNullParameter(trailDot.getMultimedias(), "multimedia");
		
		userService.validateUserAction(trailDot.getTrail().getUser().getId());
		
	}
	
	@Override
	public void validateDeleteTrailDot(final TrailDot trailDot) {
		userService.validateUserAction(trailDot.getTrail().getUser().getId());
	}
	
	@Override
	public Boolean existTrailDot(final Long trailDotId) {
		if(!trailDotRepository.exists(trailDotId)) {
			throw new IllegalArgumentException("Id n&atilde;o encontrado.");
		}

		return Boolean.TRUE;
		
	}

}
