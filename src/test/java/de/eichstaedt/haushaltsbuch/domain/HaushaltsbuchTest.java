package de.eichstaedt.haushaltsbuch.domain;

import static org.hamcrest.core.Is.is;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */
public class HaushaltsbuchTest {

  @Test
  public void testCreation() {

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Erstes Haushaltsbuch");

    Assert.assertThat(haushaltsbuch.getErstellDatum(),is(LocalDate.now()));

    Assert.assertThat(haushaltsbuch.getAusgaben().isEmpty(),is(true));

    Assert.assertThat(haushaltsbuch.getEinnahmen().isEmpty(),is(true));

    }

}
