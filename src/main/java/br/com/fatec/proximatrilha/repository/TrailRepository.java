package br.com.fatec.proximatrilha.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.Trail;

public interface TrailRepository extends CrudRepository<Trail, Long> {
	
	public List<Trail> findByNameContainsIgnoreCase(final String name);
	
}
