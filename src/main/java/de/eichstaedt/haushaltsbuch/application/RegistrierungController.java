package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@Controller
public class RegistrierungController {

  @Autowired
  private Registrierung registrierung;

  @Autowired
  private BenutzerBoundaryController benutzerBoundaryController;


  private static final Logger logger = LoggerFactory.getLogger(RegistrierungController.class);

  @RequestMapping(value = "registrierung")
  public String registration(Model model) {

    logger.info("GET Request for registration page");

    model.addAttribute("registrierung", registrierung);

    return "registrierung";
  }

  @RequestMapping(value = "registrierung/aktivierung/{code}")
  public ModelAndView aktivierung(Model model, @PathVariable String code) {

    logger.info("GET Request for aktivierung page for code {} ",code);

    ModelAndView modelView = new ModelAndView();

    boolean aktivierung = benutzerBoundaryController.aktiviereBenutzerMitCode(code);


    if(aktivierung)
    {

      modelView.setViewName("aktivierungerfolg");

    }else {

    }

    return modelView;
  }


  @RequestMapping(value = "/registrierung", method = RequestMethod.POST)
  public ModelAndView register(@Valid Registrierung registrierungForm, BindingResult bindingResult) {

    logger.info("Get registration form (POST) {} ",registrierungForm.toString());

    ModelAndView modelView = new ModelAndView();

    Benutzer benutzer = benutzerBoundaryController.erstelleAnwendungsBenutzerVonRegistrierung(registrierungForm);

    if(Objects.nonNull(benutzer))
    {

      modelView.setViewName("registrierungerfolg");

    }else
    {
      modelView.setViewName("registrierungfehler");
    }

    return modelView;
  }

}
