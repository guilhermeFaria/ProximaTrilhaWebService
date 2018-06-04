package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.TrailDot;

public interface TrailDotValidatorService {

	public void validateCreateTrailDot(final Long userId, final Long trailId, final TrailDot trailDot);

	public void validateUpdateTrailDot(final Long userId, final Long trailId, final Long trailDotId, final TrailDot trailDot);
	
	public Boolean existTrailDot(final Long trailDotId);

	public void validateDeleteTrailDot(final TrailDot trailDot);
}
