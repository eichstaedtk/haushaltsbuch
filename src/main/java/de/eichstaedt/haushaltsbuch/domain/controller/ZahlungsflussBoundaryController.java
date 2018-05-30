package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;

public interface ZahlungsflussBoundaryController {

    public boolean buchen(Haushaltsbuch haushaltsbuch, Zahlungsfluss zahlungs);

}
