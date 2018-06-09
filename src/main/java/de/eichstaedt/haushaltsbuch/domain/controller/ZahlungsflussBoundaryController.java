package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.application.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZahlungsflussBoundaryController {

    boolean buchen(Haushaltsbuch haushaltsbuch, Zahlungsfluss zahlungs);

    Optional<Zahlungsfluss> laden(String zahlungsid);

    boolean loeschen(String haushaltsbuchid, String zahlungid);

    Page<Zahlungsfluss> findAllPageable(Pageable pageable, Long buchid);

    JahresberichtModel createJahresbericht(Long buchid, int year);
}
