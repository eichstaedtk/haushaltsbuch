package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by konrad.eichstaedt@gmx.de on 29.05.18.
 */
public interface KategorieRepository extends CrudRepository<Kategorie,String> {

}
