package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */
public class ZahlungsflussTest {

  @Test
  public void testCreation() {

    EinmaligeZahlung zahlungsfluss = new EinmaligeZahlung("Beschreibung",new BigDecimal(2.45),new Kategorie("Test"),LocalDate.now(),Zahlungstyp.AUSGABE,Zahlungsintervall.EINMALIG,1l);

    Assert.assertThat(zahlungsfluss.getTyp(),is(Zahlungstyp.AUSGABE));

    Assert.assertThat(zahlungsfluss.getBeschreibung(),is("Beschreibung"));

    Assert.assertThat(zahlungsfluss.getBetrag(),is(new BigDecimal(2.45)));

    Assert.assertThat(zahlungsfluss.getBuchungsTag(),is(LocalDate.now()));

    Assert.assertThat(zahlungsfluss.getZahlungsintervall(),is(Zahlungsintervall.EINMALIG));

    Assert.assertThat(zahlungsfluss.getBuchid(),is(1l));
  }
}
