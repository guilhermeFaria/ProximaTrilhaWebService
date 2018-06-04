package br.com.fatec.proximatrilha.service;

import java.util.Collection;
import java.util.Set;

import br.com.fatec.proximatrilha.model.Authorization;
import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.model.User;

public interface UserService {

	public Collection<User> getAll();

	public User create(final User user);

	public User getById(final Long userId);

	public User update(final Long userId, User user);

	public void delete(final Long userId);

	public Collection<Authorization> assignRole(final Long userId, Set<Authorization> authoritys);

	public User assignTrails(final Trail trailCreated);

	public User removeRole(final Long userId, final Set<Authorization> authoritys);

	public User removeTrail(final User user, final Trail trail);

	public Collection<Authorization> getRoles(final Long userId);

	public Collection<Trail> getTrailsByUserId(final Long userId);
	
	public User getUserFromSession();
	
	public void validateUserAction(final Long userId);

}
