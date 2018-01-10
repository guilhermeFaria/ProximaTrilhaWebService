package br.com.fatec.proximatrilha.service;

import java.util.Collection;

import br.com.fatec.proximatrilha.model.Trail;

public interface TrailService {

	public Collection<Trail> getAll();

	public Trail create(final Trail trail);

	public Trail getById(final Long trailId);

	public Trail update(final Long trailId, final Trail trail);

	public void delete(final Long trailId);

}
