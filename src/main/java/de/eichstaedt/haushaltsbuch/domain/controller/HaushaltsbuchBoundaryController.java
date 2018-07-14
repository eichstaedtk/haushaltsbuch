package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.List;
import java.util.Optional;

/**
 * Created by konrad.eichstaedt@gmx.de on 14.07.18.
 */
public interface HaushaltsbuchBoundaryController {

  Haushaltsbuch createHaushaltsbuch(String name, String benutzerName);

  List<Haushaltsbuch> findAllHaushaltsbuecher(String benutzername);

  Optional<Haushaltsbuch> findById(Long buchid);

}
