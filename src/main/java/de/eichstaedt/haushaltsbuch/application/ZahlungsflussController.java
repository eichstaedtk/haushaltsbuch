package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 27.05.18.
 */

@Controller
public class ZahlungsflussController {

  private static final Logger logger = LoggerFactory.getLogger(ZahlungsflussController.class);


  @PostMapping("/zahlungen")
  public ModelAndView buchen(ModelMap model,@AuthenticationPrincipal User accountDetails,@ModelAttribute Zahlungsfluss zahlung, BindingResult bindingResult) {

    logger.info("Getting POST with Zahung Binding");

    bindingResult.getAllErrors().stream().forEach(e -> {
      logger.info(e.toString());
    });

    logger.info("Getting POST Request with zahlung {} ",zahlung);

    return new ModelAndView("redirect:dashboard",model);
  }

}
