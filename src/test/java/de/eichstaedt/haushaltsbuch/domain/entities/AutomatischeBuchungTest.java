package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.eichstaedt.haushaltsbuch.domain.repository.AutomatischeBuchungRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.ZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-05.
 */
public class AutomatischeBuchungTest {

  @Test
  public void testCreation() {

    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),
        Zahlungstyp.AUSGABE,Long.valueOf(1l));

    AutomatischeBuchung versicherung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        zahlungsfluss,Zahlungsintervall.MONATLICH);

    assertNotNull(versicherung);

    assertEquals(Long.valueOf(1),versicherung.getZahlung().getBuchid());

    assertEquals(LocalDate.of(2020,1,1),versicherung.getStartTag());

    assertEquals(LocalDate.of(2020,12,31),versicherung.getEndTag());

    assertEquals(Zahlungsintervall.MONATLICH,versicherung.getZahlungsintervall());

    assertTrue(versicherung.isAktiv());
  }

  @Test
  public void testistBuchungNotwendig() {

    AutomatischeBuchung versicherung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),
            Zahlungstyp.AUSGABE,Long.valueOf(1l)),Zahlungsintervall.MONATLICH);

    assertTrue(versicherung.istBuchungNotwendig(LocalDate.now()));

  }

  @Test
  public void testSpeichern() {

    AutomatischeBuchungRepository repository = Mockito.mock(AutomatischeBuchungRepository.class);

    AutomatischeBuchung buchung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),
            Zahlungstyp.AUSGABE,Long.valueOf(1l)),Zahlungsintervall.MONATLICH);

    buchung.speichern(repository);

    verify(repository,times(1)).save(buchung);
  }

  @Test
  public void testautomatischBuchen() {

    AutomatischeBuchungRepository repository = Mockito.mock(AutomatischeBuchungRepository.class);
    ZahlungsflussRepository zahlungsflussRepository = Mockito.mock(ZahlungsflussRepository.class);
    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),Zahlungstyp.AUSGABE,Long.valueOf(1l));

    Zahlungsfluss zahlungsflussClone = zahlungsfluss.clone();
    zahlungsflussClone.setBuchungsTag(LocalDate.now());

    AutomatischeBuchung buchung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),zahlungsfluss,
    Zahlungsintervall.MONATLICH);

    when(zahlungsflussRepository.save(zahlungsflussClone)).thenReturn(zahlungsflussClone);

    buchung.automatischBuchen(zahlungsflussRepository,repository);

    verify(zahlungsflussRepository,times(1)).save(any());

    assertNotNull(buchung.getLetzteBuchungAm());

    assertTrue(buchung.getBuchungen().size() == 1);

    verify(repository,times(1)).save(buchung);

  }
}
