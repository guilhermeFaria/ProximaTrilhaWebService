package br.com.fatec.proximatrilha.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.Authorization;

public interface AuthorizationRepository extends CrudRepository<Authorization, Long> {

	public List<Authorization> findByAuthorityContainsIgnoreCase(final String authority);
	
	public Authorization findTop1ByAuthorityContainsIgnoreCase(final String authority);
	
}
