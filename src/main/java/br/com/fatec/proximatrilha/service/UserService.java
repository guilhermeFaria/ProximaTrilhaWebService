package br.com.fatec.proximatrilha.service;

import java.util.Collection;

import br.com.fatec.proximatrilha.model.User;

public interface UserService {

	public Collection<User> getAll();

	public User create(User user);

	public User getById(Long userId);

	public User update(Long userId, User user);

	public void delete(Long userId);

}
