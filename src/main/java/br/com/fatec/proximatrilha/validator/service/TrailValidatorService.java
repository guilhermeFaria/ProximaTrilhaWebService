package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.Trail;

public interface TrailValidatorService {

	public void validateUpdateTrail(final Long trailId, final Trail trail, final Trail trailOld);

	public void validateCreateTrail(final Trail trail);

	public Boolean existTrail(final Long trailId);

}
