package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

public class HaushaltsbuchTest {

  @Test
  public void testCreation() {

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    Mockito.when(passwordEncoder.encode("Start123")).thenReturn("3432423");

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Buch 2018",benutzer);

    Assert.assertThat(haushaltsbuch.getBesitzer(),is(benutzer));

    Assert.assertThat(haushaltsbuch.getErstellDatum(),is(LocalDate.now()));

    Assert.assertThat(haushaltsbuch.getName(),is("Buch 2018"));

    Assert.assertThat(haushaltsbuch.getAusgaben().size(),is(0));

    Assert.assertThat(haushaltsbuch.getEinnahmen().size(),is(0));
  }
}
