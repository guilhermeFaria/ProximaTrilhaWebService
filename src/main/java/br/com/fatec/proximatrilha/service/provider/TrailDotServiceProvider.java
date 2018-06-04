package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Comment;
import br.com.fatec.proximatrilha.model.Multimedia;
import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.repository.CommentRepository;
import br.com.fatec.proximatrilha.repository.MultimediaRepository;
import br.com.fatec.proximatrilha.repository.TrailDotRepository;
import br.com.fatec.proximatrilha.repository.TrailRepository;
import br.com.fatec.proximatrilha.service.MultimediaService;
import br.com.fatec.proximatrilha.service.TrailDotService;
import br.com.fatec.proximatrilha.service.TrailService;
import br.com.fatec.proximatrilha.validator.service.TrailDotValidatorService;

@Service("trailDotService")
@Transactional
public class TrailDotServiceProvider implements TrailDotService {
	
	@Autowired
	private TrailDotRepository trailDotRepository;
	
	@Autowired
	private TrailDotValidatorService trailDotValidatorService;
		
	@Autowired
	private TrailService trailService;
	
	@Autowired
	private TrailRepository trailRepository;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public static Logger logger = Logger.getLogger("trail");
	
	public void setMultimediaService(MultimediaService multimediaService) {
		this.multimediaService = multimediaService;
	}

	public void setTrailDotRepository(TrailDotRepository trailDotRepository) {
		this.trailDotRepository = trailDotRepository;
	}
	
	public void setTrailRepository(TrailRepository trailRepository) {
		this.trailRepository = trailRepository;
	}

	public void setTrailDotValidatorService(TrailDotValidatorService trailDotValidatorService) {
		this.trailDotValidatorService = trailDotValidatorService;
	}
	
	public Collection<TrailDot> getAll(final Long trailId) {
		Trail trail = trailService.getById(trailId);
		return trail.getTrailDots();
	}
	
	@Override
	public TrailDot create(final Long trailId, TrailDot trailDot) {
		TrailDot trailDotCreated = new TrailDot();
		Trail trail = trailService.getById(trailId);
		trailDotValidatorService.validateCreateTrailDot(trail.getUser().getId(), trailId, trailDot);
		
		try {
			trailDot.setTrail(trail);
			
			if(!trailDot.getMultimedias().isEmpty()) {
				trailDot.setComments(new HashSet<Comment>());
				
				Set<Multimedia> multimediasBackup = trailDot.getMultimedias();
				Set<Multimedia> multimedias = new HashSet<Multimedia>();
				trailDot.setMultimedias(new HashSet<Multimedia>());
				
				trailDotCreated = trailDotRepository.save(trailDot);
				
				for (Multimedia multimedia: multimediasBackup) {
					multimedias.add(multimediaService.create(trailId, trailDotCreated.getId(), multimedia));
				}
				trailDotCreated.setMultimedias(multimedias);
				
			}else {
				trailDotCreated = trailDotRepository.save(trailDot);
			}
			
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao criar um novo ponto da trilha." + ex.getStackTrace());
			throw new IllegalArgumentException("Ocorreu erro ao criar um novo ponto da trilha");
		}
		return trailDotCreated;
	}
	
	@Override
	public TrailDot getById(final Long trailId, final Long trailDotId) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		
		return trailDotRepository.findOne(trailDotId);
	}
	
	@Override
	public TrailDot update(final Long trailId, final Long trailDotId, final TrailDot trailDot) {
		TrailDot trailDotUpdate = null;
		Trail trail = trailService.getById(trailId);
		trailDotValidatorService.validateUpdateTrailDot(trail.getUser().getId(), trailId, trailDotId, trailDot);
		try {
			trailDot.setTrail(trail);
			trailDotUpdate = trailDotRepository.save(trailDot);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao atualizar as informa&ccedil;&otilde;es do ponto de trilha." 
					+ ex.getStackTrace());
		}
		return trailDotUpdate;
	}
	
	@Override
	public void delete(final Long trailId, final Long trailDotId) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		 
		TrailDot trailDot = getById(trailId, trailDotId);
		Trail trail = trailDot.getTrail();
		Set<TrailDot> trailDots = trail.getTrailDots();
		trailDotValidatorService.validateDeleteTrailDot(trailDot);
		try {
			if(!trailDot.getMultimedias().isEmpty()) {
				multimediaRepository.delete(trailDot.getMultimedias());
			}
			if(!trailDot.getComments().isEmpty()) {
				commentRepository.delete(trailDot.getComments());
			}
			trailDots.remove(trailDot);
			trail.setTrailDots(trailDots);
			trailRepository.save(trail);
			trailDotRepository.delete(trailDotId);
			
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao remover o ponto de trilha." +ex.getStackTrace());
		}
	}
	
	@Override
	public void delete(final Set<TrailDot> trailDots) {
		try {
			for(TrailDot trailDot: trailDots) {
				if(!trailDot.getMultimedias().isEmpty()) {
					multimediaRepository.delete(trailDot.getMultimedias());
				}
				if(!trailDot.getComments().isEmpty()) {
					commentRepository.delete(trailDot.getComments());
				}
			}
			trailDotRepository.delete(trailDots);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao remover os pontos de trilha." +ex.getStackTrace());
		}
	}
}