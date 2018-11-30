package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import java.util.Optional;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */
public interface BenutzerBoundaryController {

  Optional<Benutzer> erstelleUndSpeichereBenutzerAusRegistrierung(Registrierung registrierung);

  boolean isBenutzernameFreiZurVerwendung(String benutzername);

  boolean aktiviereBenutzerMitCode(String code);
}
