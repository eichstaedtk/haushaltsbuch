package de.eichstaedt.haushaltsbuch.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class HaushaltsbuchControllerTest {


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
  public void testHaushaltsbuchPage() throws Exception {

    this.mockMvc.perform(get("/haushaltsbuch?buchid=1").with(user("konrad").password("Start123"))).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("<title>Haushaltsbuch</title>")))
        .andExpect(content().string(containsString("<h3>Testbuch</h3>")));
  }

}
