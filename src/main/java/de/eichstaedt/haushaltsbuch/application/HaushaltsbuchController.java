package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.services.HaushaltsbuchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

  @PostMapping("/haushaltsbuch")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String haushaltsbuchName) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", haushaltsbuchName);

    model.addAttribute("user",accountDetails);

    Haushaltsbuch haushaltsbuch = haushaltsbuchService.createHaushaltsbuch(haushaltsbuchName,accountDetails.getUsername());

    logger.info("Neues Haushaltsbuch erstellt {} ", haushaltsbuch);

    return new ModelAndView("redirect:/dashboard",model);
  }

}
