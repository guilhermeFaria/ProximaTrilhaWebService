package br.com.fatec.proximatrilha.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fatec.proximatrilha.model.TrailDot;

public interface TrailDotRepository extends CrudRepository<TrailDot, Long> {
	
	public List<TrailDot> findByNameContainsIgnoreCase(final String name);
}
