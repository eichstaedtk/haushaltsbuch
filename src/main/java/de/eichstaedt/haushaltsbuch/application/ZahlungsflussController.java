package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.services.HaushaltsbuchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by konrad.eichstaedt@gmx.de on 27.05.18.
 */

@Controller
public class ZahlungsflussController {

  private static final Logger logger = LoggerFactory.getLogger(ZahlungsflussController.class);

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  @Autowired
  private HaushaltsbuchService haushaltsbuchService;

  @RequestMapping( value = "/haushaltsbuch/{buchid}/zahlungen", method = RequestMethod.POST)
  public ModelAndView buchen(ModelMap model, @AuthenticationPrincipal User accountDetails, @ModelAttribute Zahlungsfluss zahlung, BindingResult bindingResult, @PathVariable String buchid) {

    logger.info("Getting POST with Zahung Binding");

    bindingResult.getAllErrors().stream().forEach(e -> {
      logger.info(e.toString());
    });

    logger.info("Getting POST Request with zahlung {} ",zahlung);

    Optional<Haushaltsbuch> buch = haushaltsbuchService.findAllHaushaltsbuecher(accountDetails.getUsername())
            .stream().filter(b -> String.valueOf(b.getId()).equals(buchid)).findFirst();

    if(buch.isPresent())
    {
      zahlungsflussBoundaryController.buchen(buch.get(),zahlung);
    }

    return new ModelAndView("redirect:/haushaltsbuch?buchid="+buch.get().getId(),model);
  }

}
