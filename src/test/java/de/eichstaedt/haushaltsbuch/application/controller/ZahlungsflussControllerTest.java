package de.eichstaedt.haushaltsbuch.application.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligerZahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by konrad.eichstaedt@gmx.de on 14.07.18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ZahlungsflussControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();

    haushaltsbuchBoundaryController.createHaushaltsbuch("Testbuch","konrad");
  }

  @Test
  public void testBuchen() throws Exception {

    EinmaligerZahlungsfluss zahlungsfluss = new EinmaligerZahlungsfluss("Beschreibung",new BigDecimal(15.00),new Kategorie("Versicherung"),LocalDate.now(),Zahlungstyp.AUSGABE,1L);

    this.mockMvc.perform(post("/haushaltsbuch/1/zahlungen").with(user("konrad").password("Start123")).contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("beschreibung", zahlungsfluss.getBeschreibung())
        .param("betrag", zahlungsfluss.getBetrag().toString())
        .param("kategorie", zahlungsfluss.getKategorie().getName())
        .param("buchungsTag", "20-02-2018")
        .param("typ", zahlungsfluss.getTyp().toString().toUpperCase()))
        .andExpect(status().is3xxRedirection());
  }


}
