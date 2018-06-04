package br.com.fatec.proximatrilha.validator.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.repository.TrailRepository;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;

@Service("trailValidatorService")
@Transactional
public class TrailValidatorServiceProvider implements TrailValidatorService {
	
	@Autowired
	private TrailRepository trailRepository;
	
	@Autowired
	private UserService userService;

	public void setTrailRepository(TrailRepository trailRepository) {
		this.trailRepository = trailRepository;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void validateUpdateTrail(final Long trailId, final Trail trail, final Trail trailOld) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trail.getId(), "trailid");
		if (!trailId.equals(trail.getId())) {
			throw new IllegalArgumentException();
		}
		notNullParameter(trail.getName(), "name");
		notNullParameter(trail.getDescription(), "email");
		notNullParameter(trail.getStartPoint(), "startPoint");
		notNullParameter(trail.getEndPoint(), "endPoint");
		notNullParameter(trail.getTrailDots(), "trailDots");
		
		userService.validateUserAction(trail.getUser().getId());
		
	}

	@Override
	public void validateCreateTrail(final Trail trail) {
		notNullParameter(trail.getName(), "name");
		notNullParameter(trail.getDescription(), "description");
		notNullParameter(trail.getStartPoint(), "startPoint");
		notNullParameter(trail.getEndPoint(), "endPoint");
	}
	
	@Override
	public Boolean existTrail(final Long trailId) {
		if(!trailRepository.exists(trailId)) {
			throw new IllegalArgumentException("Id n&atilde;o encontrado.");
		}

		return Boolean.TRUE;
		
	}

}