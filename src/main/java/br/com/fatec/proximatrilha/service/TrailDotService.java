package br.com.fatec.proximatrilha.service;

import java.util.Collection;
import java.util.Set;

import br.com.fatec.proximatrilha.model.TrailDot;

public interface TrailDotService {
	
	public Collection<TrailDot> getAll(final Long trailId);

	public TrailDot create(final Long trailId, TrailDot trailDot);

	public TrailDot getById(final Long trailId, final Long trailDotId);

	public TrailDot update(final Long trailId, final Long trailDotId, final TrailDot trailDot);

	public void delete(final Long trailId, final Long trailDotId);

	public void delete(final Set<TrailDot> trailDots);	
}
