package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligeZahlung;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by konrad.eichstaedt@gmx.de on 27.05.18.
 */

@Controller
public class ZahlungsflussController {

  private static final Logger logger = LoggerFactory.getLogger(ZahlungsflussController.class);

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  @RequestMapping( value = "/haushaltsbuch/{buchid}/zahlungen", method = RequestMethod.POST)
  public ModelAndView buchen(ModelMap model, @AuthenticationPrincipal User accountDetails, @ModelAttribute("neuezahlung") @Valid EinmaligeZahlung neuezahlung, BindingResult binding, @PathVariable String buchid,
      RedirectAttributes redirectAttributes) {

    logger.info("Getting POST Binding for Zahlung {} ", neuezahlung);

    if(binding.hasErrors()) {

      binding.getAllErrors().stream().forEach(e -> {
        logger.info(e.toString());
      });

      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.neuezahlung",binding);
      redirectAttributes.addFlashAttribute("neuezahlung",neuezahlung);


      return new ModelAndView("redirect:/haushaltsbuch?buchid="+buchid);
    }

    Optional<Haushaltsbuch> buch = haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername())
            .stream().filter(b -> String.valueOf(b.getId()).equals(buchid)).findFirst();

    if(buch.isPresent())
    {
      zahlungsflussBoundaryController.buchen(buch.get(),neuezahlung);
    }

    return new ModelAndView("redirect:/haushaltsbuch?buchid="+buch.get().getId());
  }

  @RequestMapping( value = "/haushaltsbuch/{buchid}/zahlungen/loeschen", method = RequestMethod.GET)
  public ModelAndView loeschen(ModelMap model, @AuthenticationPrincipal User accountDetails,@PathVariable String buchid,@RequestParam(value = "zahlungsid") String zahlungsid) {

    logger.info("Getting GET Löschen for Zahlung {} ", zahlungsid);

    zahlungsflussBoundaryController.loeschen(buchid,zahlungsid);

    return new ModelAndView("redirect:/haushaltsbuch?buchid="+buchid);
  }

}
