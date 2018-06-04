package br.com.fatec.proximatrilha.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findByNameContainsIgnoreCase(final String name);
	
	public User findByEmail(final String email);
	
}
