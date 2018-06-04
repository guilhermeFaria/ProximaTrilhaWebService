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

import br.com.fatec.proximatrilha.model.Trail;
import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.model.User;
import br.com.fatec.proximatrilha.repository.TrailRepository;
import br.com.fatec.proximatrilha.service.TrailDotService;
import br.com.fatec.proximatrilha.service.TrailService;
import br.com.fatec.proximatrilha.service.UserService;
import br.com.fatec.proximatrilha.validator.service.TrailValidatorService;

@Service("trailService")
@Transactional
public class TrailServiceProvider implements TrailService {

	@Autowired
	private TrailRepository trailRepository;
	
	@Autowired
	private TrailValidatorService trailValidatorService;
	
	@Autowired
	private TrailDotService trailDotService;
	
	@Autowired
	private UserService userService;
	
	public static Logger logger = Logger.getLogger("trail");
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTrailDotService(TrailDotService trailDotService) {
		this.trailDotService = trailDotService;
	}

	public void setTrailRepository(TrailRepository trailRepository) {
		this.trailRepository = trailRepository;
	}

	public void setTrailValidatorService(TrailValidatorService trailValidatorService) {
		this.trailValidatorService = trailValidatorService;
	}

	public Collection<Trail> getAll() {
		logger.log(Level.INFO, "Iniciando a coleta de trails");
		Set<Trail> trails  = new HashSet<Trail>();
		try {
			for (Trail trail: trailRepository.findAll()) {
				trails.add(trail);
			}
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro no retorno das trilhas "+ex.getStackTrace());
		}
		logger.log(Level.INFO, "Finalizado o processo");
		return trails;
	}

	public Trail getById(final Long trailId) {
		logger.log(Level.INFO, "Iniciando a busca pela trail atraves do Id");
		
		Trail trail = new Trail();
		try {
			 trail = trailRepository.findOne(trailId);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro no retorno da trilha pelo id. "+ex.getStackTrace());
		}
		logger.log(Level.INFO, "Finalizado o processo.");
		return trail;
	}
	
	public Trail create(final Trail trail) {
		trailValidatorService.validateCreateTrail(trail);
		User user = userService.getUserFromSession();
		trail.setUser(user);
		Trail trailCreated = new Trail();
		try {
			if(!trail.getTrailDots().isEmpty()) {
				Trail trailWithoutDot = trail;
				Set<TrailDot> trailDotsBackup = trail.getTrailDots();
				Set<TrailDot> trailDots = new HashSet<TrailDot>();
				trailWithoutDot.setTrailDots(new HashSet<TrailDot>());
				trailCreated = trailRepository.save(trailWithoutDot);
				for (TrailDot trailDot: trailDotsBackup) {
					trailDots.add(trailDotService.create(trailCreated.getId(), trailDot));
				}
				trailCreated.setTrailDots(trailDots);
			}else {
				trailCreated = trailRepository.save(trail);
			}
			
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao criar uma nova trilha." + ex.getStackTrace());
		}
		return trailCreated;
	}

	public Trail update(final Long trailId, Trail trail) {
		Trail trailOld = getById(trailId);
		trailValidatorService.validateUpdateTrail(trailId, trail, trailOld);
		trail.setUser(trailOld.getUser());
		trail.setTrailDots(new HashSet<TrailDot>());
		try {
			trail = trailRepository.save(trail);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu um erro na atualização da trilha."+ex.getStackTrace());
		}
		return trail;
	}

	public void delete(final Long trailId) {
		notNullParameter(trailId, "trailId");
		trailValidatorService.existTrail(trailId);
		try {
			Trail trail = trailRepository.findOne(trailId);
			User user = trail.getUser();
			userService.validateUserAction(user.getId());
			userService.removeTrail(user, trail);
			trailDotService.delete(trail.getTrailDots());
			trailRepository.delete(trailId);
		}catch (Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro no processo de remoção." + ex.getStackTrace());
		}
			
	}
}