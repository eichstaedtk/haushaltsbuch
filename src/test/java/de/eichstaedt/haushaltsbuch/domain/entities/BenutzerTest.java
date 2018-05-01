package de.eichstaedt.haushaltsbuch.domain.entities;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 01.05.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BenutzerTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testCreationWithBuilder() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();

    Assert.assertThat(benutzer,is(notNullValue()));

    Assert.assertThat(benutzer.isAktiviert(),is(false));
  }
}
