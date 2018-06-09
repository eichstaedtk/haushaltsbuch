package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ZahlungsflussRepository extends PagingAndSortingRepository<Zahlungsfluss,Long> {

  Page<Zahlungsfluss> findAllByBuchid(Pageable pageable,Long buchid);

  List<Zahlungsfluss> findByBuchidAndBuchungsTagBetween(Long buchid,LocalDate start, LocalDate end);

}
