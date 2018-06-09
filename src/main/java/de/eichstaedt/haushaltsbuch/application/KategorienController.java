package de.eichstaedt.haushaltsbuch.application;

import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.06.18.
 */

@RestController
public class KategorienController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchController.class);

  @Autowired
  private KategorieBoundaryController kategorieBoundaryController;

  @PostMapping("/kategorien")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String kategorienname) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", kategorienname);

    kategorieBoundaryController.createKategorie(kategorienname);

    return new ModelAndView("redirect:/dashboard",model);
  }

}
