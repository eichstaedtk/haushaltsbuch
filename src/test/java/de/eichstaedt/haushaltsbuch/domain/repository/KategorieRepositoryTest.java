package de.eichstaedt.haushaltsbuch.domain.repository;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class KategorieRepositoryTest {

  @Autowired
  private KategorieRepository kategorieRepository;


  @Test
  public void testSave() {

    Kategorie kategorie = new Kategorie("VersicherungsTest");

    Kategorie saved = kategorieRepository.save(kategorie);

    Assert.assertThat(kategorieRepository.findById("VersicherungsTest").get(),is(kategorie));

    kategorieRepository.deleteById("VersicherungsTest");
  }

  @Test
  public void testDelete() {

    Kategorie kategorie = new Kategorie("VersicherungLoeschen");

    kategorieRepository.save(kategorie);

    kategorieRepository.deleteById("VersicherungLoeschen");

    Assert.assertThat(kategorieRepository.findById("VersicherungLoeschen"),is(Optional.empty()));
  }
}
