package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.06.18.
 */

@Controller
public class HaushaltsberichtController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsberichtController.class);

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  @GetMapping("/haushaltsbericht")
  public ModelAndView oeffnen(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "buchid") String buchid)
  {

    logger.info("Request GET for haushaltsbericht");

    JahresberichtModel jahresberichtModel = zahlungsflussBoundaryController.createJahresbericht(Long.parseLong(buchid),LocalDate.now().getYear());

    model.addAttribute("ausgaben",jahresberichtModel.getAusgaben());
    model.addAttribute("einnahmen",jahresberichtModel.getEinnahmen());
    model.addAttribute("titel",jahresberichtModel.getTitel());

    return new ModelAndView("haushaltsbericht",model);
  }

}
