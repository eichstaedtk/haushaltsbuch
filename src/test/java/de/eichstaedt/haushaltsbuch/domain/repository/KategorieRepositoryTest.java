package de.eichstaedt.haushaltsbuch.domain.repository;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
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

  @Before
  public void setUp() throws Exception {
    kategorieRepository.deleteAll();
  }

  @Test
  public void testSave() {

    Kategorie kategorie = new Kategorie("Versicherung");

    Kategorie saved = kategorieRepository.save(kategorie);

    Assert.assertThat(kategorieRepository.findById("Versicherung").get(),is(kategorie));
  }

  @Test
  public void testDelete() {

    Kategorie kategorie = new Kategorie("Versicherung");

    kategorieRepository.save(kategorie);

    kategorieRepository.deleteById("Versicherung");

    Assert.assertThat(kategorieRepository.findById("Versicherung"),is(Optional.empty()));
  }
}
