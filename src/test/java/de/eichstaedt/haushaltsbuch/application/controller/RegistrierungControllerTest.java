package de.eichstaedt.haushaltsbuch.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
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
public class RegistrierungControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void testRegistrierungPage() throws Exception {

    this.mockMvc.perform(get("/registrierung")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("<title>Haushaltsbuch Registrierung</title>")))
        .andExpect(content().string(containsString("<form class=\"form-horizontal\" method=\"post\" action=\"registrierung\">")));
  }

  @Test
  public void testRegister() throws Exception {

    Registrierung registrierung = new Registrierung.RegistrierungBuilder("konrad.eichstaedt@gmx.de","konrad123","Start123","Start123")
        .withName("Konrad","Eichstädt")
        .build();

    this.mockMvc.perform(post("/registrierung").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("vorname", registrierung.getVorname())
        .param("nachname", registrierung.getNachname())
        .param("benutzername", registrierung.getBenutzername())
        .param("passwort", registrierung.getPasswort())
        .param("passwortwiederholung", registrierung.getPasswortWiederholung()))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("<title>Haushaltsbuch Registrierung erfolgreich</title>")));
  }


}
