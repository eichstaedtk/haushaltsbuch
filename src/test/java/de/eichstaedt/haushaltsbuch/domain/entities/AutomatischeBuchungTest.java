package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import java.time.LocalDate;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-05.
 */
public class AutomatischeBuchungTest {

  @Test
  public void testCreation() {

    AutomatischeBuchung versicherung = new AutomatischeBuchung(1L, LocalDate.of(2020,1,1),LocalDate.of(2020,12,31),
        Zahlungsintervall.MONATLICH);

    assertNotNull(versicherung);

    assertEquals(Long.valueOf(1),versicherung.getHaushaltsbuchID());

    assertEquals(LocalDate.of(2020,1,1),versicherung.getStartTag());

    assertEquals(LocalDate.of(2020,12,31),versicherung.getEndTag());

    assertEquals(Zahlungsintervall.MONATLICH,versicherung.getZahlungsintervall());
  }
}
