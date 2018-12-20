package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.application.model.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.application.model.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligeZahlung;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZahlungsflussBoundaryController {

    boolean buchen(Haushaltsbuch haushaltsbuch, EinmaligeZahlung zahlungs);

    Optional<EinmaligeZahlung> laden(String zahlungsid);

    boolean loeschen(String haushaltsbuchid, String zahlungid);

    Page<EinmaligeZahlung> findAllPageable(Pageable pageable, Long buchid);

    JahresberichtModel createJahresbericht(Long buchid, int year);

    KategorieBerichtModel createJahresKategoriebericht(Long buchid, int year);
}
