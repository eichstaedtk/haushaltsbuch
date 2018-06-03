package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ZahlungsflussRepository extends PagingAndSortingRepository<Zahlungsfluss,Long> {
}
