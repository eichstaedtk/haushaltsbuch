package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.repository.KategorieRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
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
public class KategorieServiceTest {

  @Autowired
  private KategorieService kategorieService;

  @Autowired
  private KategorieRepository kategorieRepository;

  @Test
  public void testcreateDefaultKategories() {

    kategorieRepository.deleteAll();

    kategorieService.createDefaultKategories();

    Assert.assertThat(kategorieService.findAll().size(),is(5));
  }

  @Test
  public void testcreateKategorie() {

    kategorieService.createKategorie("Neue Kategorie");

    Assert.assertThat(kategorieService.findAll().contains(new Kategorie("Neue Kategorie")),is(true));

    kategorieRepository.deleteById("Neue Kategorie");

  }
}
