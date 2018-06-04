package br.com.fatec.proximatrilha.validator.service;

import br.com.fatec.proximatrilha.model.Multimedia;
import br.com.fatec.proximatrilha.model.TrailDot;

public interface MultimediaValidatorService {

	public void validateCreateMultimedia(final Long trailId, final Long trailDotId, final TrailDot trailDot, final Multimedia multimedia);

	public void validateUpdateTrail(final Long trailId, final Long trailDotId, final TrailDot trailDot, final Long multimediaId, final Multimedia multimedia);

	public Boolean existMultimedia(final Long multimediaId);

	public void validateDeleteMultimedia(final Multimedia multimedia);
	
}
