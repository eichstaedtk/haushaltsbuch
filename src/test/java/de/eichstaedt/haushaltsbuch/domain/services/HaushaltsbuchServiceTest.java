package de.eichstaedt.haushaltsbuch.domain.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
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
public class HaushaltsbuchServiceTest {

  @Autowired
  private HaushaltsbuchService haushaltsbuchService;

  @Test
  public void testcreateHaushaltsbuch() {

    Haushaltsbuch haushaltsbuch = haushaltsbuchService.createHaushaltsbuch("Buch 2018 Konrad","steffen");

    Assert.assertThat(haushaltsbuch,is(nullValue()));

    haushaltsbuch = haushaltsbuchService.createHaushaltsbuch("Buch 2018 Konrad","konrad");

    Assert.assertThat(haushaltsbuch,is(notNullValue()));

    Assert.assertThat(haushaltsbuch.getName(),is("Buch 2018 Konrad"));
  }

  @Test
  public void testfindAllHaushaltsbuecher() {

    Haushaltsbuch haushaltsbuch1 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/1 Konrad","konrad");

    Haushaltsbuch haushaltsbuch2 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/2 Konrad","konrad");

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher("konrad").size(),is(2));

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher("konrad").contains(haushaltsbuch1),is(true));

    Assert.assertThat(haushaltsbuchService.findAllHaushaltsbuecher("konrad").contains(haushaltsbuch2),is(true));
  }

  @Test
  public void testfindById() {

    Optional<Haushaltsbuch> haushaltsbuch = haushaltsbuchService.findById(1L);

    Assert.assertThat(haushaltsbuch,is(Optional.empty()));

    Haushaltsbuch haushaltsbuch1 = haushaltsbuchService.createHaushaltsbuch("Buch 2018/1 Konrad","konrad");

    Optional<Haushaltsbuch> result = haushaltsbuchService.findById(haushaltsbuch1.getId());

    Assert.assertThat(result,is(Optional.of(haushaltsbuch1)));
  }
}
