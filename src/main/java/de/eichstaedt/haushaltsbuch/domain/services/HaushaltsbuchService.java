package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */

@Component
public class HaushaltsbuchService {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchService.class);

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private HaushaltsbuchRepository haushaltsbuchRepository;

  public Haushaltsbuch createHaushaltsbuch(String name, String benutzerName)
  {

    logger.info("Tryijg to create  haushaltsbuch with name {} for benutzer {} ", name, benutzerName);

    Haushaltsbuch result = null;

    Benutzer benutzer = benutzerRepository.findByBenutzername(benutzerName);

    result = new Haushaltsbuch(name,benutzer);

    result = haushaltsbuchRepository.save(result);

    return result;
  }

}
