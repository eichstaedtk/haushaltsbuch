package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HaushaltsbuchServiceTest {

  @Autowired
  private HaushaltsbuchService haushaltsbuchService;

  @Autowired
  private HaushaltsbuchRepository haushaltsbuchRepository;

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @After
  public void tearDown() throws Exception {
    haushaltsbuchRepository.deleteAll();
    benutzerRepository.deleteAll();
  }

  @Before
  public void setUp() throws Exception {
    haushaltsbuchRepository.deleteAll();
    benutzerRepository.deleteAll();
  }

  @Test
  public void testcreateHaushaltsbuch() {

    Haushaltsbuch haushaltsbuch = haushaltsbuchService.createHaushaltsbuch("Buch 2018 Konrad","steffen");

    Assert.assertThat(haushaltsbuch,is(nullValue()));

    Benutzer benutzer = new Benutzer.BenutzerBuilder("klaus123","konrad@gmx.de","Start123",passwordEncoder)
        .withName("Konrad","Eichstädt")
        .withWohnort("Göttliner Dorfstrasse 12a","14712","Ratenow","Deutschland")
        .build();


    benutzerRepository.save(benutzer);

    haushaltsbuch = haushaltsbuchService.createHaushaltsbuch("Buch 2018 Konrad",benutzer.getBenutzername());

    Assert.assertThat(haushaltsbuch,is(notNullValue()));

    Assert.assertThat(haushaltsbuch.getName(),is("Buch 2018 Konrad"));

  }

  @Test
  public void testfindAllHaushaltsbuecher() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("klaus123","konrad@gmx.de","Start123",passwordEncoder)
        .withName("Konrad","Eichstädt")
        .withWohnort("Göttliner Dorfstrasse 12a","14712","Ratenow","Deutschland")
        .build();


    benutzerRepository.save(benutzer);


    haushaltsbuchRepository.deleteAll();

    Haushaltsbuch haushaltsbuch1 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/1 Konrad",benutzer.getBenutzername());

    Haushaltsbuch haushaltsbuch2 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/2 Konrad",benutzer.getBenutzername());

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher(benutzer.getBenutzername()).size(),is(2));

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher(benutzer.getBenutzername()).contains(haushaltsbuch1),is(true));

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher(benutzer.getBenutzername()).contains(haushaltsbuch2),is(true));
  }

  @Test
  public void testfindById() {

    Benutzer benutzer = new Benutzer.BenutzerBuilder("klaus123","konrad@gmx.de","Start123",passwordEncoder)
        .withName("Konrad","Eichstädt")
        .withWohnort("Göttliner Dorfstrasse 12a","14712","Ratenow","Deutschland")
        .build();


    benutzerRepository.save(benutzer);

    Haushaltsbuch haushaltsbuch1 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/1 Konrad",benutzer.getBenutzername());

    Optional<Haushaltsbuch> result = haushaltsbuchService.findById(haushaltsbuch1.getId());

    Assert.assertThat(result,is(Optional.of(haushaltsbuch1)));
  }
}
