package br.com.fatec.proximatrilha.service;

import java.util.Collection;
import java.util.List;

import br.com.fatec.proximatrilha.model.Authorization;

public interface AuthorizationService {

	public Collection<Authorization> getAll();

	public Authorization create(final Authorization authorization);

	public Authorization getById(final Long authorizationId);

	public Authorization update(final Long authorizationId, final Authorization authorization);

	public void delete(final Long authorizationId);
	
	public List<Authorization> getByName(final String name); 

}
