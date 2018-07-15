package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import org.junit.After;
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
public class BenutzerServiceTest {

  @Autowired
  private BenutzerService benutzerService;

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @After
  public void setup() {

    benutzerRepository.deleteAll();
  }

  @Test
  public void testerstelleAnwendungsBenutzerVonRegistrierungNotValid() {

    Registrierung registrierung = new Registrierung();

    Benutzer benutzer = benutzerService.erstelleAnwendungsBenutzerVonRegistrierung(registrierung);

    Assert.assertThat(benutzer,is(nullValue()));
  }

  @Test
  public void testerstelleAnwendungsBenutzerVonRegistrierungNotValid1() {

    Registrierung registrierung = new Registrierung("konrad","konrad.eichstaedt@gmx.de","Start123");

    Benutzer benutzer = benutzerService.erstelleAnwendungsBenutzerVonRegistrierung(registrierung);

    Assert.assertThat(benutzer,is(notNullValue()));

    Assert.assertThat(benutzerRepository.findById(benutzer.getBenutzername()).isPresent(),is(true));

    Assert.assertThat(benutzer.isAktiviert(),is(false));
  }

  @Test
  public void testisBenutzernameFree() {

    Assert.assertThat(benutzerService.isBenutzernameFree("konrad"),is(true));

  }

  @Test
  public void testisBenutzernameFreeFalse() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();

    benutzerRepository.save(benutzer);

    Assert.assertThat(benutzerService.isBenutzernameFree("konrad"),is(false));

  }

}
