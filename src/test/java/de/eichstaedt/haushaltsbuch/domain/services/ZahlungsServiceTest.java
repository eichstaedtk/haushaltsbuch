package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.application.model.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.application.model.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.KategorieRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.ZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZahlungsServiceTest {

  @Autowired
  private ZahlungsService zahlungsService;

  @Autowired
  private HaushaltsbuchRepository haushaltsbuchRepository;

  @Autowired
  private KategorieRepository kategorieRepository;

  @Autowired
  private ZahlungsflussRepository zahlungsflussRepository;

  private Logger logger = LoggerFactory.getLogger(ZahlungsServiceTest.class);

  @Test
  public void testbuchen() {

    Haushaltsbuch buch = createHaushaltsbuch();

    Kategorie versicherung = createKategorie();

    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("Beschreibung", new BigDecimal(2.45),
        versicherung, LocalDate
        .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, 1l);

    boolean gebucht = zahlungsService.buchen(buch, zahlungsfluss);

    Assert.assertThat(gebucht, is(true));

  }

  private Kategorie createKategorie() {
    Kategorie kategorie = new Kategorie("Versicherung");

    return kategorieRepository.save(kategorie);
  }

  private Haushaltsbuch createHaushaltsbuch() {
    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    Mockito.when(passwordEncoder.encode("Start123")).thenReturn("3432423");

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad", "konrad.eichstaedt@gmx.de",
        "Start123", passwordEncoder).build();

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Buch 2018", benutzer);

    return haushaltsbuchRepository.save(haushaltsbuch);
  }

  @Test
  public void testbuchenNullCheck() {
    Assert.assertThat(zahlungsService.buchen(null, null), is(false));
  }

  @Test
  public void testladen() {

    Kategorie versicherung = createKategorie();

    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("Beschreibung", new BigDecimal(2.45),
        versicherung, LocalDate
        .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, 1l);

    zahlungsfluss = zahlungsflussRepository.save(zahlungsfluss);

    Optional<Zahlungsfluss> result = zahlungsService.laden(String.valueOf(zahlungsfluss.getId()));

    Assert.assertThat(result, is(Optional.of(zahlungsfluss)));
  }

  @Test
  public void testloeschen() {

    Haushaltsbuch buch = createHaushaltsbuch();

    Kategorie versicherung = createKategorie();

    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("Beschreibung", new BigDecimal(2.45),
        versicherung, LocalDate
        .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, 1l);

    zahlungsService.buchen(buch, zahlungsfluss);

    boolean geloescht = zahlungsService
        .loeschen(String.valueOf(buch.getId()), String.valueOf(zahlungsfluss.getId()));

    Assert.assertThat(geloescht, is(true));

    Assert.assertThat(zahlungsService.laden(String.valueOf(zahlungsfluss.getId())),
        is(Optional.empty()));
  }

  @Test
  public void testcreateJahresbericht() {

    Haushaltsbuch buch = createHaushaltsbuch();

    Kategorie versicherung = createKategorie();

    Zahlungsfluss ausgabe = new Zahlungsfluss("Beschreibung", new BigDecimal(2.45), versicherung,
        LocalDate
            .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, buch.getId());

    Zahlungsfluss ausgabe1 = new Zahlungsfluss("Beschreibung", new BigDecimal(5.55), versicherung,
        LocalDate
            .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, buch.getId());

    Zahlungsfluss einahme = new Zahlungsfluss("Beschreibung", new BigDecimal(155.55), versicherung,
        LocalDate
            .now(), Zahlungstyp.EINNAHME, Zahlungsintervall.EINMALIG, buch.getId());

    zahlungsflussRepository.saveAll(Arrays.asList(ausgabe, ausgabe1, einahme));

    JahresberichtModel jahresberichtModel = zahlungsService
        .createJahresbericht(buch.getId(), LocalDate.now().getYear());

    Assert.assertThat(jahresberichtModel.getTitel(),
        is("Jahresbericht " + LocalDate.now().getYear() + " Buch 2018"));

    Assert.assertThat(Arrays.asList(jahresberichtModel.getAusgaben()).size(), is(12));

    Assert.assertThat(
        Arrays.asList(jahresberichtModel.getAusgaben()).get(LocalDate.now().getMonthValue() - 1)
            .doubleValue(),
        is(ausgabe.getBetrag().doubleValue() + ausgabe1.getBetrag().doubleValue()));

    Assert.assertThat(
        Arrays.asList(jahresberichtModel.getEinnahmen()).get(LocalDate.now().getMonthValue() - 1)
            .doubleValue(), is(einahme.getBetrag().doubleValue()));

  }

  @Test
  public void testcreateJahresKategoriebericht() {

    Haushaltsbuch buch = createHaushaltsbuch();

    Kategorie versicherung = createKategorie();

    Zahlungsfluss ausgabe = new Zahlungsfluss("Beschreibung", new BigDecimal(2.45), versicherung,
        LocalDate
            .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, buch.getId());

    Zahlungsfluss ausgabe1 = new Zahlungsfluss("Beschreibung", new BigDecimal(5.55), versicherung,
        LocalDate
            .now(), Zahlungstyp.AUSGABE, Zahlungsintervall.EINMALIG, buch.getId());

    Zahlungsfluss einahme = new Zahlungsfluss("Beschreibung", new BigDecimal(155.55),
        new Kategorie("Gehalt"), LocalDate
        .now(), Zahlungstyp.EINNAHME, Zahlungsintervall.EINMALIG, buch.getId());

    zahlungsflussRepository.saveAll(Arrays.asList(ausgabe, ausgabe1, einahme));

    KategorieBerichtModel kategorieBerichtModel = zahlungsService
        .createJahresKategoriebericht(buch.getId(), LocalDate.now().getYear());

    Assert.assertThat(kategorieBerichtModel.getTitel(),
        is("Kategorien Ausgaben Jahresbericht " + LocalDate.now().getYear() + " Buch 2018"));

    Object[][] values = kategorieBerichtModel.getKategorieValues();

    BigDecimal result = new BigDecimal(0.00);

    for(int i=0 ;i < values.length;i++) {

      logger.info("Check values {} , amount {}", values[i], values[i][0]);

      if("Versicherung".equals(String.valueOf(values[i][0])))
      {
        result = new BigDecimal(String.valueOf(values[i][1]));
      }
    }

    Assert.assertThat(result.doubleValue(),is(ausgabe.getBetrag().add(ausgabe1.getBetrag()).doubleValue()));

  }
}