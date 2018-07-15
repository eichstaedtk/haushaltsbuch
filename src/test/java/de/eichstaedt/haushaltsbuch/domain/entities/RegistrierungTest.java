package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.Matchers.is;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */
public class RegistrierungTest {

  @Test
  public void testCreation() {

    Registrierung registrierung = new Registrierung.RegistrierungBuilder("konrad.eichstaedt@gmx.de","konrad123","Start123","Start123")
        .withName("Konrad","Eichstädt")
        .build();

    Assert.assertThat(registrierung.getEmail(),is("konrad.eichstaedt@gmx.de"));
    Assert.assertThat(registrierung.getBenutzername(),is("konrad123"));
    Assert.assertThat(registrierung.getPasswort(),is("Start123"));
    Assert.assertThat(registrierung.getPasswortWiederholung(),is("Start123"));
    Assert.assertThat(registrierung.getVorname(),is("Konrad"));
    Assert.assertThat(registrierung.getNachname(),is("Eichstädt"));
  }
}
