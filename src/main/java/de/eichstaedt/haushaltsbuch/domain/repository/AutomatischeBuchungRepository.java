package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.AutomatischeBuchung;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-07.
 */
public interface AutomatischeBuchungRepository extends CrudRepository<AutomatischeBuchung,Long> {

}
