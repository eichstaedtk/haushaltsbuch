package de.eichstaedt.haushaltsbuch.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@Controller
public class RegistrierungController {

  @Autowired
  private Registrierung registrierung;


  private static final Logger logger = LoggerFactory.getLogger(RegistrierungController.class);

  @RequestMapping(value = "registrierung")
  public String registration(Model model) {

    logger.info("GET Request for registration page");

    model.addAttribute("registration", registrierung);

    return "registrierung";
  }

}
