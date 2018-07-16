package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltbuchLoeschenFailedException;
import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */

@Component
public class HaushaltsbuchService implements HaushaltsbuchBoundaryController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchService.class);

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private HaushaltsbuchRepository haushaltsbuchRepository;

  @Override
  public Haushaltsbuch createHaushaltsbuch(String name, String benutzerName)
  {

    logger.info("Trying to create  haushaltsbuch with name {} for benutzer {} ", name, benutzerName);

    Haushaltsbuch result = null;

    Benutzer benutzer = benutzerRepository.findByBenutzername(benutzerName);

    logger.info("Find Benutzer to create Haushaltsbuch {} ", benutzer);

    if(Objects.nonNull(benutzer)) {

      result = new Haushaltsbuch(name, benutzer);

      result = haushaltsbuchRepository.save(result);

    }

    return result;
  }

  @Override
  public void loeschen(Long id, String benutzerName) throws HaushaltbuchLoeschenFailedException {

    logger.info("Trying to delete haushaltsbuch with id {} ", id);

    Optional<Haushaltsbuch> haushaltsbuch = haushaltsbuchRepository.findById(id);

    if(haushaltsbuch.isPresent())
    {
      Haushaltsbuch buch = haushaltsbuch.get();

      if(buch.getEinnahmen().isEmpty() && buch.getAusgaben().isEmpty() && buch.getBesitzer().getBenutzername().equals(benutzerName))
      {
        haushaltsbuchRepository.deleteById(buch.getId());

      }else {
        throw new HaushaltbuchLoeschenFailedException("Haushaltsbuch enthält Ausgaben oder Einnahmen oder gehört einem anderen Benutzer");
      }
    }

  }

  @Override
  public List<Haushaltsbuch> findAllHaushaltsbuecher(String benutzername) {

    List<Haushaltsbuch> result = new ArrayList<>();

    logger.info("Finding all haushaltsbucher of size {} ", haushaltsbuchRepository.findAll());

    result.addAll(haushaltsbuchRepository.findByBesitzerBenutzername(benutzername));

    return result;
  }

  @Override
  public Optional<Haushaltsbuch> findById(Long buchid) {
    return haushaltsbuchRepository.findById(buchid);
  }

}
