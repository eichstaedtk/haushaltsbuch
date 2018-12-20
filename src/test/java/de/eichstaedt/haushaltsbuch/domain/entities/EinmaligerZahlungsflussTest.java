package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */
public class EinmaligerZahlungsflussTest {

  @Test
  public void testCreation() {

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(2.45),new Kategorie("Test"),LocalDate.now(),Zahlungstyp.AUSGABE,1l);

    Assert.assertThat(zahlungsfluss.getTyp(),is(Zahlungstyp.AUSGABE));

    Assert.assertThat(zahlungsfluss.getBeschreibung(),is("Beschreibung"));

    Assert.assertThat(zahlungsfluss.getBetrag(),is(new BigDecimal(2.45)));

    Assert.assertThat(zahlungsfluss.getBuchungsTag(),is(LocalDate.now()));

    Assert.assertThat(zahlungsfluss.getBuchid(),is(1l));
  }
}
