package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */

@Controller
public class HaushaltsbuchController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchController.class);

  @Autowired
  private BenutzerRepository benutzerRepository;

  @PostMapping("/haushaltsbuch")
  public String neuesHaushaltsbuch(Model model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String haushaltsbuchName) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", haushaltsbuchName);

    Benutzer benutzer = benutzerRepository.findByBenutzername(accountDetails.getUsername());

    Haushaltsbuch haushaltsbuch = new Haushaltsbuch(haushaltsbuchName,benutzer);

    model.addAttribute("user",accountDetails);

    logger.info("Neues Haushaltsbuch erstellt {} ", haushaltsbuch);

    return "dashboard";
  }

}
