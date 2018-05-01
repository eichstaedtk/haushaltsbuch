package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.application.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */

@Component
public class BenutzerService implements BenutzerBoundaryController
{

  private static final Logger logger = LoggerFactory.getLogger(BenutzerService.class);

  @Override
  public Benutzer erstelleAnwendungsBenutzerVonRegistrierung(Registrierung registrierung) {

    if(Objects.nonNull(registrierung))
    {

      logger.info("Erstelle Benutzer von Registreirung {} ", registrierung);

      Benutzer benutzer = new Benutzer.BenutzerBuilder(registrierung.getBenutzername(),registrierung.getEmail(),registrierung.getPasswort()).build();

    }

    return null;
  }
}
