package br.com.fatec.proximatrilha.service;

import java.util.Collection;

import br.com.fatec.proximatrilha.model.Multimedia;

public interface MultimediaService {

	public Collection<Multimedia> getAll(final Long trailId, final Long trailDotId);

	public Multimedia getById(final Long multimediaId);

	public Multimedia create(final Long trailId, final Long trailDotId, Multimedia multimedia);

	public Multimedia update(final Long trailId, final Long trailDotId, final Long multimediaId, Multimedia multimedia);

	public void delete(final Long trailId, final Long trailDotId, final Long multimediaId);
	
	public void delete(final Long trailId, final Long trailDotId, final Collection<Multimedia> multimedias);
	
}
