package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.services.HaushaltsbuchService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */

@Controller
public class HaushaltsbuchController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchController.class);

  @Autowired
  private HaushaltsbuchService haushaltsbuchService;

  private Zahlungsfluss neueZahlung;

  @PostMapping("/haushaltsbuch")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String haushaltsbuchName) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", haushaltsbuchName);

    model.addAttribute("user",accountDetails);

    Haushaltsbuch haushaltsbuch = haushaltsbuchService.createHaushaltsbuch(haushaltsbuchName,accountDetails.getUsername());

    logger.info("Neues Haushaltsbuch erstellt {} ", haushaltsbuch);

    return new ModelAndView("redirect:/dashboard",model);
  }

  @GetMapping("/haushaltsbuch")
  public ModelAndView oeffnen(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "buchid") String buchid) {

    logger.info("Getting GET request for oeffen haushalstbuch {} ", buchid);

    List<Haushaltsbuch> buecher = haushaltsbuchService.findAllHaushaltsbuecher(accountDetails.getUsername());

    Optional<Haushaltsbuch> buch = buecher.stream().filter(b -> String.valueOf(b.getId()).equals(buchid)).findFirst();

    if(buch.isPresent()) {

      logger.info("Found buch with id {} buch {} ", buchid,buch);

      neueZahlung = new Zahlungsfluss();

      model.addAttribute("buch",buch.get());
      model.addAttribute("neuezahlung",neueZahlung);
    }

    return new ModelAndView("haushaltsbuch",model);
  }

  public Zahlungsfluss getNeueZahlung() {
    return neueZahlung;
  }
}
