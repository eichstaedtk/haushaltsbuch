package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.application.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */
public interface BenutzerBoundaryController {

  Benutzer erstelleAnwendungsBenutzerVonRegistrierung(Registrierung registrierung);

}
