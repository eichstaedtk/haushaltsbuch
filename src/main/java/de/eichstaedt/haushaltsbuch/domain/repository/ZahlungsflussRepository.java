package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import org.springframework.data.repository.CrudRepository;

public interface ZahlungsflussRepository extends CrudRepository<Zahlungsfluss,Long> {
}
