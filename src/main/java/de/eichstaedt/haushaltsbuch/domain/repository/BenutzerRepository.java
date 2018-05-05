package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */
public interface BenutzerRepository extends CrudRepository<Benutzer,String> {

  Benutzer findByBenutzername(String benutzername);

  Benutzer findByAktivierungsCode(String aktivierungsCode);

}
