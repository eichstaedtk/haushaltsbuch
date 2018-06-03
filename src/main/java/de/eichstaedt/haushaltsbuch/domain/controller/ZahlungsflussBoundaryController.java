package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import java.util.Optional;

public interface ZahlungsflussBoundaryController {

    boolean buchen(Haushaltsbuch haushaltsbuch, Zahlungsfluss zahlungs);

    Optional<Zahlungsfluss> laden(String zahlungsid);

    boolean loeschen(String haushaltsbuchid, String zahlungid);
}
