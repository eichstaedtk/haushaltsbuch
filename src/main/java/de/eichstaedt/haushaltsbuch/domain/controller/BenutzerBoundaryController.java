package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */
public interface BenutzerBoundaryController {

  Benutzer erstelleAnwendungsBenutzerVonRegistrierung(Registrierung registrierung);

  boolean isBenutzernameFree(String benutzername);

  boolean aktiviereBenutzerMitCode(String code);
}
