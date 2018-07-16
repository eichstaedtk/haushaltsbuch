package de.eichstaedt.haushaltsbuch.domain.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HaushaltsbuchRepositoryTest {

  @Autowired
  private HaushaltsbuchRepository haushaltsbuchRepository;

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Before
  public void setUp() throws Exception {
    haushaltsbuchRepository.deleteAll();
    benutzerRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    haushaltsbuchRepository.deleteAll();
    benutzerRepository.deleteAll();
  }

  @Test
  public void testSave() {

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    Mockito.when(passwordEncoder.encode("Start123")).thenReturn("3432423");

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();
    benutzer = benutzerRepository.save(benutzer);

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Buch 2018",benutzer);

    Assert.assertThat(haushaltsbuch.getId(),is(nullValue()));

    Haushaltsbuch saved = haushaltsbuchRepository.save(haushaltsbuch);

    Assert.assertThat(saved.getId(),is(notNullValue()));

    Assert.assertThat(saved.getName(),is("Buch 2018"));

    haushaltsbuchRepository.deleteById(saved.getId());
  }

  @Test
  public void testDelete() {

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    Mockito.when(passwordEncoder.encode("Start123")).thenReturn("3432423");

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();
    benutzer = benutzerRepository.save(benutzer);

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Buch 2018",benutzer);

    Haushaltsbuch saved = haushaltsbuchRepository.save(haushaltsbuch);

    haushaltsbuchRepository.delete(saved);

    Assert.assertThat(haushaltsbuchRepository.findById(saved.getId()),is(Optional.empty()));
  }

  @Test
  public void testfindByBesitzerBenutzername() {

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    Mockito.when(passwordEncoder.encode("Start123")).thenReturn("3432423");

    Benutzer benutzer = new Benutzer.BenutzerBuilder("konrad","konrad.eichstaedt@gmx.de","Start123",passwordEncoder).build();
    benutzer = benutzerRepository.save(benutzer);

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch("Buch 2018",benutzer);

    Haushaltsbuch saved = haushaltsbuchRepository.save(haushaltsbuch);

    Assert.assertThat(haushaltsbuchRepository.findByBesitzerBenutzername("konrad").get(0),is(saved));

    haushaltsbuchRepository.deleteById(saved.getId());

  }
}
