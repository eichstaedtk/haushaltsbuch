package de.eichstaedt.haushaltsbuch.application;

import static org.hamcrest.core.Is.is;

import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.infrastructure.BenutzerRepository;
import org.junit.Assert;
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
public class BenutzernameValidatorTest {

  @Autowired
  private BenutzerBoundaryController benutzerBoundaryController;

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Before
  public void setup() {
    benutzerRepository.deleteAll();
  }

  @Test
  public void testisValid() {

    BenutzernameValidator validator = new BenutzernameValidator(benutzerBoundaryController);

    Assert.assertThat(validator.isValid("konrad",null),is(true));

  }

  @Test
  public void testisValidWithFalse() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad@gmx.de","Start123",passwordEncoder).build();

    benutzerRepository.save(benutzer);

    BenutzernameValidator validator = new BenutzernameValidator(benutzerBoundaryController);

    Assert.assertThat(validator.isValid("konrad",null),is(false));

  }
}
