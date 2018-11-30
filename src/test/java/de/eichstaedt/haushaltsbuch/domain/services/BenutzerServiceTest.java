package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
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

  @Before
  public void setUp() throws Exception {
    benutzerRepository.deleteAll();
  }

  @After
  public void setup() {

    benutzerRepository.deleteAll();
  }

  @Test
  public void testerstelleAnwendungsBenutzerVonRegistrierungNotValid() {

    Registrierung registrierung = new Registrierung();

    Optional<Benutzer> benutzer = benutzerService.erstelleUndSpeichereBenutzerAusRegistrierung(registrierung);

    assertThat(benutzer.isPresent(),is(false));
  }

  @Test
  public void testerstelleAnwendungsBenutzerVonRegistrierungValid() {

    Registrierung registrierung = new Registrierung("konrad","konrad.eichstaedt@gmx.de","Start123");

    Optional<Benutzer> benutzer = benutzerService.erstelleUndSpeichereBenutzerAusRegistrierung(registrierung);

    assertThat(benutzer.isPresent(),is(true));

    assertThat(benutzerRepository.findById(benutzer.get().getBenutzername()).isPresent(),is(true));

    assertThat(benutzer.get().isAktiviert(),is(false));
  }

  @Test
  public void testisBenutzernameFreiZurVerwendungTrue() {

    assertThat(benutzerService.isBenutzernameFreiZurVerwendung("konrad"),is(true));

  }

  @Test
  public void testisBenutzernameFreiZurVerwendungFalse() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();

    benutzerRepository.save(benutzer);

    assertThat(benutzerService.isBenutzernameFreiZurVerwendung("konrad"),is(false));

  }

  @Test
  public void testaktiviereBenutzerMitCode() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();

    benutzerRepository.save(benutzer);

    boolean aktiviert = benutzerService.aktiviereBenutzerMitCode(benutzer.getAktivierungsCode());

    assertThat(aktiviert,is(true));

    Benutzer aktivierterBenutzer = benutzerRepository.findByBenutzername(benutzer.getBenutzername());

    assertThat(aktivierterBenutzer.getAktivierungsCode(),is(nullValue()));

    assertThat(aktivierterBenutzer.getAktivierungBis(),is(nullValue()));

  }
}
