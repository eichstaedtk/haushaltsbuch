package de.eichstaedt.haushaltsbuch.domain.repository;

import static org.hamcrest.core.Is.is;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
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
public class BenutzerRepositoryTest {

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testSave() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("klaus","konrad@gmx.de","Start123",passwordEncoder)
        .withName("Klaus","Eichstädt")
        .withWohnort("Göttliner Dorfstrasse 12a","14712","Ratenow","Deutschland")
        .build();


    benutzerRepository.save(benutzer);

    Assert.assertThat(benutzerRepository.findById(benutzer.getBenutzername()).isPresent(),is(true));

  }

  @Test
  public void testDeleteById() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("klaus123","konrad@gmx.de","Start123",passwordEncoder)
        .withName("Konrad","Eichstädt")
        .withWohnort("Göttliner Dorfstrasse 12a","14712","Ratenow","Deutschland")
        .build();


    benutzerRepository.save(benutzer);

    Assert.assertThat(benutzerRepository.findById(benutzer.getBenutzername()).isPresent(),is(true));

    benutzerRepository.deleteById(benutzer.getBenutzername());

    Assert.assertThat(benutzerRepository.findById(benutzer.getBenutzername()).isPresent(),is(false));
  }
}
