package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by konrad.eichstaedt@gmx.de on 2019-08-07.
 *
 * Use case controller for all views related to Haushaltsbücher.
 */

@Controller
@Component(value = "buecherController")
public class BuecherController {

  private static final Logger logger = LoggerFactory.getLogger(BuecherController.class);

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  @GetMapping(value = "/buecher")
  public String buecherView(Model model,@AuthenticationPrincipal User accountDetails, @RequestParam(required = false) String active) {

    logger.info("Calling Buecher View {} ",active);

    if(accountDetails != null) {

      List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController
          .findAllHaushaltsbuecher(accountDetails.getUsername());

      if (Objects.nonNull(haushaltsbuecher)) {
        model.addAttribute("haushaltsbuecher", haushaltsbuecher);
      }

      SubnavigationModel subnavigationModel = new SubnavigationModel("Bücher", Arrays.asList("Meine Bücher","Neues Buch erstellen"));
      subnavigationModel.setActiveItem(active);

      model.addAttribute("subnav",subnavigationModel);
    }

    return "/buecher";
  }

}
