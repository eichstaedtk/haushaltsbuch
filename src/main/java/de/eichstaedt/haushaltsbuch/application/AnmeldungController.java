package de.eichstaedt.haushaltsbuch.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by konrad.eichstaedt@gmx.de on 21.05.18.
 */

@Controller
public class AnmeldungController {

  private static final Logger logger = LoggerFactory.getLogger(AnmeldungController.class);

  @GetMapping(value = "anmeldung")
  public String anmeldung(Model model, @RequestParam(value = "error", required = false) boolean error) {

    logger.info("GET Request for anmeldung page");

    return "/anmeldung";
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String exception(final Exception exception, final Model model) {
    logger.info("Exception during execution of SpringSecurity application", exception);
    String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
    model.addAttribute("errorMessage", errorMessage);

    return "/anmeldung";
  }

}
