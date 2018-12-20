package de.eichstaedt.haushaltsbuch.domain.repository;

import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligerZahlungsfluss;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EinmalZahlungsflussRepository extends PagingAndSortingRepository<EinmaligerZahlungsfluss,Long> {

  Page<EinmaligerZahlungsfluss> findAllByBuchid(Pageable pageable,Long buchid);

  List<EinmaligerZahlungsfluss> findByBuchidAndBuchungsTagBetween(Long buchid,LocalDate start, LocalDate end);

}
