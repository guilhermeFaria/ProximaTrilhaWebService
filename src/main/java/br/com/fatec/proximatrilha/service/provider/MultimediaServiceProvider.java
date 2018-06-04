package br.com.fatec.proximatrilha.service.provider;

import static br.com.fatec.proximatrilha.utils.ValidateUtils.notNullParameter;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.proximatrilha.model.Multimedia;
import br.com.fatec.proximatrilha.model.TrailDot;
import br.com.fatec.proximatrilha.repository.MultimediaRepository;
import br.com.fatec.proximatrilha.service.MultimediaService;
import br.com.fatec.proximatrilha.service.TrailDotService;
import br.com.fatec.proximatrilha.validator.service.MultimediaValidatorService;

@Service("multimediaService")
@Transactional
public class MultimediaServiceProvider implements MultimediaService {
	
	@Autowired
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	private MultimediaValidatorService multimediaValidatorService;
	
	@Autowired
	private TrailDotService trailDotService;
	
	public static Logger logger = Logger.getLogger("trail");
	
	public void setTrailDotService(TrailDotService trailDotService) {
		this.trailDotService = trailDotService;
	}

	public void setMultimediaRepository(MultimediaRepository multimediaRepository) {
		this.multimediaRepository = multimediaRepository;
	}

	public void setMultimediaValidatorService(MultimediaValidatorService multimediaValidatorService) {
		this.multimediaValidatorService = multimediaValidatorService;
	}

	@Override
	public Collection<Multimedia> getAll(final Long trailId, final Long trailDotId) {
		notNullParameter(trailId, "trailId");
		notNullParameter(trailDotId, "trailDotId");
		TrailDot trailDot = trailDotService.getById(trailId, trailDotId);
		return trailDot.getMultimedias();
	}

	@Override
	public Multimedia getById(final Long multimediaId) {
		return multimediaRepository.findOne(multimediaId);
	}

	@Override
	public Multimedia create(final Long trailId, final Long trailDotId, Multimedia multimedia) {
		Multimedia multimediaCreated = null;
		TrailDot trailDot = trailDotService.getById(trailId, trailDotId);
		multimediaValidatorService.validateCreateMultimedia(trailId, trailDotId, trailDot, multimedia);
		try {
			multimedia.setInsertDate(new Timestamp(System.currentTimeMillis()));
			multimedia.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			multimedia.setTrailDot(trailDot);
			multimediaCreated = multimediaRepository.save(multimedia);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao inserir um arquivo de multimidia." + ex.getStackTrace());
		}
		
		return multimediaCreated;
	} 

	@Override
	public Multimedia update(final Long trailId, final Long trailDotId, final Long multimediaId, Multimedia multimedia) {
		Multimedia multimediaUpdated = null;
		TrailDot trailDot = trailDotService.getById(trailId, trailDotId);
		multimediaValidatorService.validateUpdateTrail(trailId, trailDotId, trailDot, multimediaId, multimedia);
		try {
			multimedia.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			multimedia.setTrailDot(trailDot);
			multimediaUpdated = multimediaRepository.save(multimedia);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao atualizar as informa&ccedil;&otilde;es do arquivo de multimidia." 
					+ex.getStackTrace());
		}
		return multimediaUpdated;
	}
	
	@Override
	public void delete(final Long trailId, final Long trailDotId, final Long multimediaId) {
		notNullParameter(multimediaId, "trailId");
		multimediaValidatorService.existMultimedia(multimediaId);
		
		try {
			Multimedia multimedia = getById(multimediaId);
			multimediaValidatorService.validateDeleteMultimedia(multimedia);
			multimediaRepository.delete(multimedia);
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Ocorreu erro ao remover o arquivo de multimidia." 
					+ex.getStackTrace());
		}
	}
	
	@Override
	@Transactional
	public void delete(final Long trailId, final Long trailDotId, final Collection<Multimedia> multimedias) {
		multimediaRepository.delete(multimedias);
	}
	
}
