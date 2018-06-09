package de.eichstaedt.haushaltsbuch.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @GetMapping("/haushaltsbericht")
  public ModelAndView oeffnen(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "buchid", required = false) String buchid)
  {

    logger.info("Request GET for haushaltsbericht");


    model.addAttribute("ausgaben",new double[]{23,56,100,45.60,100,450});
    model.addAttribute("einnahmen",new double[]{10,206,3000,450.60,0,4150});

    return new ModelAndView("haushaltsbericht",model);
  }

}
