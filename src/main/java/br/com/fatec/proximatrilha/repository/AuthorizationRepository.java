package br.com.fatec.proximatrilha.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.Authorization;

public interface AuthorizationRepository extends CrudRepository<Authorization, Long> {

	public List<Authorization> findByNameContainsIgnoreCase(final String name);
	
}
