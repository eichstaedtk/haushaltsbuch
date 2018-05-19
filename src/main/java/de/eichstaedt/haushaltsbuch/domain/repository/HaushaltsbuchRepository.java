package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */
public interface HaushaltsbuchRepository extends CrudRepository<Haushaltsbuch,Long> {

  List<Haushaltsbuch> findByBesitzerBenutzername(String benutzername);

}
