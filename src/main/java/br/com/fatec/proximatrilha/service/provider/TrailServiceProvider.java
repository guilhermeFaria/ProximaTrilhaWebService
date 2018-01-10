package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.repository.TrailRepository;
import br.com.fatec.proximatrilha.service.TrailService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;

@Service("trailService")
@Transactional
public class TrailServiceProvider implements TrailService {

	@Autowired
	private TrailRepository trailRepository;
	
	@Autowired
	private TrailValidatorService trailValidatorService;
	
	public void setTrailRepository(TrailRepository trailRepository) {
		this.trailRepository = trailRepository;
	}

	public void setTrailValidatorService(TrailValidatorService trailValidatorService) {
		this.trailValidatorService = trailValidatorService;
	}

	public Collection<Trail> getAll() {
		List<Trail> trails  = new ArrayList<Trail>();
		for (Trail trail: trailRepository.findAll()) {
			trails.add(trail);
		}
		return trails;
	}

	public Trail create(final Trail trail) {
		trailValidatorService.validateCreateTrail(trail);
		return trailRepository.save(trail);
	}

	public Trail getById(final Long trailId) {
		return trailRepository.findOne(trailId);
	}

	public Trail update(Long trailId, Trail trail) {
		trailValidatorService.validateUpdateTrail(trailId, trail);
		return trailRepository.save(trail);
	}

	public void delete(Long trailId) {
		notNullParameter(trailId, "trailId");
		if (!trailRepository.exists(trailId)) {
			throw new IllegalArgumentException("Id não encontrado.");
		}
		else {
			trailRepository.delete(trailId);
		}	
	}
}