package de.eichstaedt.haushaltsbuch.domain.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligerZahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class EinmalZahlungsflussRepositoryTest {

  @Autowired
  private EinmalZahlungsflussRepository einmalZahlungsflussRepository;

  @Autowired
  private KategorieRepository kategorieRepository;

  @Before
  public void setUp() throws Exception {
    einmalZahlungsflussRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    einmalZahlungsflussRepository.deleteAll();
  }

  @Test
  public void testSaved() {

    Kategorie kategorie = new Kategorie("Versicherung");

    Kategorie versicherung = kategorieRepository.save(kategorie);

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(2.45),versicherung,LocalDate
        .now(),Zahlungstyp.AUSGABE,1l);

    Zahlungsfluss saved = einmalZahlungsflussRepository.save(zahlungsfluss);

    Assert.assertThat(saved.getBuchid(),is(notNullValue()));

    Assert.assertThat(saved.getBuchid(),is(1l));
  }

  @Test
  public void testDelete() {

    Kategorie kategorie = new Kategorie("Versicherung");

    Kategorie versicherung = kategorieRepository.save(kategorie);

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(2.45),versicherung,LocalDate
        .now(),Zahlungstyp.AUSGABE,1l);

    EinmaligerZahlungsfluss saved = einmalZahlungsflussRepository.save(zahlungsfluss);

    einmalZahlungsflussRepository.delete(saved);

    Assert.assertThat(einmalZahlungsflussRepository.findById(saved.getId()),is(Optional.empty()));

  }

  @Test
  public void testfindAllByBuchid() {


    Kategorie kategorie = new Kategorie("Versicherung");

    Kategorie versicherung = kategorieRepository.save(kategorie);

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(2.45),versicherung,LocalDate
        .now(),Zahlungstyp.AUSGABE,1l);

    Zahlungsfluss saved = einmalZahlungsflussRepository.save(zahlungsfluss);

    Page<EinmaligerZahlungsfluss> zahlungen = einmalZahlungsflussRepository
        .findAllByBuchid(PageRequest.of(0, 5,Sort.by(
        new Order(Direction.DESC, "buchungsTag"))),1l);

    Assert.assertThat(zahlungen,is(notNullValue()));

    Assert.assertThat(zahlungen.getTotalElements(),is(1L));

    Assert.assertThat(zahlungen.getContent().get(0),is(saved));
  }

  @Test
  public void testfindByBuchidAndBuchungsTagBetween() {

    Kategorie kategorie = new Kategorie("Versicherung");

    Kategorie versicherung = kategorieRepository.save(kategorie);

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(2.45),versicherung,LocalDate
        .now(),Zahlungstyp.AUSGABE,1l);

    Zahlungsfluss saved = einmalZahlungsflussRepository.save(zahlungsfluss);

    List<EinmaligerZahlungsfluss> zahlungen = einmalZahlungsflussRepository
        .findByBuchidAndBuchungsTagBetween(1l,LocalDate.now().minusDays(1l),LocalDate.now().plusDays(1l));

    Assert.assertThat(zahlungen,is(notNullValue()));

    Assert.assertThat(zahlungen.get(0),is(saved));

    List<EinmaligerZahlungsfluss> empty = einmalZahlungsflussRepository
        .findByBuchidAndBuchungsTagBetween(1l,LocalDate.now().plusDays(1l),LocalDate.now().plusDays(2l));

    Assert.assertThat(empty,is(notNullValue()));

    Assert.assertThat(empty.isEmpty(),is(true));
  }
}
