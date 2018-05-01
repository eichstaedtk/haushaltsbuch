package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.application.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Sets;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */

@Component
public class BenutzerService implements BenutzerBoundaryController
{

  private static final Logger logger = LoggerFactory.getLogger(BenutzerService.class);

  @Override
  public Benutzer erstelleAnwendungsBenutzerVonRegistrierung(Registrierung registrierung) {

    Benutzer benutzer = null;

    if(Objects.nonNull(registrierung))
    {

      logger.info("Erstelle Benutzer von Registrierung {} ", registrierung);

      if(Stream.of(registrierung.getBenutzername(),registrierung.getPasswort(),registrierung.getPasswort()).allMatch(Objects::nonNull)) {

        benutzer = new Benutzer.BenutzerBuilder(registrierung.getBenutzername(),
            registrierung.getEmail(), registrierung.getPasswort()).build();


        if(Stream.of(registrierung.getVorname(),registrierung.getNachname()).allMatch(Objects::nonNull))
        {
          benutzer = new Benutzer.BenutzerBuilder(registrierung.getBenutzername(),
              registrierung.getEmail(), registrierung.getPasswort()).withName(registrierung.getVorname(),registrierung.getNachname()).build();

          if(Stream.of(registrierung.getLand(),registrierung.getPostleitzahl(),registrierung.getStadt(),registrierung.getStrasse()).allMatch(Objects::nonNull))
          {
            benutzer = new Benutzer.BenutzerBuilder(registrierung.getBenutzername(),
                registrierung.getEmail(), registrierung.getPasswort())
                .withName(registrierung.getVorname(),registrierung.getNachname())
                .withWohnort(registrierung.getStrasse(),registrierung.getPostleitzahl(),registrierung.getStadt(),registrierung.getLand())
                .build();
          }

        }

      }

    }

    return benutzer;
  }
}
