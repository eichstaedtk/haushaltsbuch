package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 20.12.18.
 */
public class WiederkehrenderZahlungsflussTest {

  @Test
  public void testCreation() {

    WiederkehrenderZahlungsfluss wiederkehrenderZahlungsfluss = new WiederkehrenderZahlungsfluss();

    assertThat(wiederkehrenderZahlungsfluss,is(notNullValue()));

    assertThat(wiederkehrenderZahlungsfluss instanceof Zahlungsfluss,is(true));


  }
}
