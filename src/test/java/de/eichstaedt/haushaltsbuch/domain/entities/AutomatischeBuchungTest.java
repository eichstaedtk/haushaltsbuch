package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-05.
 */
public class AutomatischeBuchungTest {

  @Test
  public void testCreation() {

    Zahlungsfluss zahlungsfluss = new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),
        Zahlungstyp.AUSGABE,Zahlungsintervall.MONATLICH,Long.valueOf(1l));

    AutomatischeBuchung versicherung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        zahlungsfluss,null,null);

    assertNotNull(versicherung);

    assertEquals(Long.valueOf(1),versicherung.getZahlung().getBuchid());

    assertEquals(LocalDate.of(2020,1,1),versicherung.getStartTag());

    assertEquals(LocalDate.of(2020,12,31),versicherung.getEndTag());

    assertEquals(Zahlungsintervall.MONATLICH,versicherung.getZahlung().getZahlungsintervall());

    assertTrue(versicherung.isAktiv());
  }

  @Test
  public void testistBuchungNotwendig() {

    AutomatischeBuchung versicherung = new AutomatischeBuchung(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        new Zahlungsfluss("KFZ Versicherung",new BigDecimal(10),new Kategorie("Versicherung"),
            Zahlungstyp.AUSGABE,Zahlungsintervall.MONATLICH,Long.valueOf(1l)),null,null);

    assertTrue(versicherung.istBuchungNotwendig(LocalDate.now()));

  }
}
