package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

  @PostMapping("/buecher")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String haushaltsbuchName) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", haushaltsbuchName);

    model.addAttribute("user",accountDetails);

    Haushaltsbuch haushaltsbuch = haushaltsbuchBoundaryController
        .createHaushaltsbuch(haushaltsbuchName,accountDetails.getUsername());

    logger.info("Neues Haushaltsbuch erstellt {} ", haushaltsbuch);

    return new ModelAndView("redirect:/buecher",model);
  }

  @GetMapping(value = "/buecher")
  public String buecherView(Model model,@AuthenticationPrincipal User accountDetails, @RequestParam(defaultValue = "Meine Bücher") String active) {

    logger.info("Calling Buecher View {} ",active);

    if(accountDetails != null) {

      List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController
          .findAllHaushaltsbuecher(accountDetails.getUsername());

      if (Objects.nonNull(haushaltsbuecher)) {
        model.addAttribute("haushaltsbuecher", haushaltsbuecher);
      }

      Map<String,String> links = new HashMap<>();
      links.put("Meine Bücher","/buecher?active=Meine Bücher");
      links.put("Neues Buch erstellen","/buecher?active=Neues Buch erstellen");


      SubnavigationModel subnavigationModel = new SubnavigationModel("Bücher", links);
      subnavigationModel.setActiveItem(active);

      model.addAttribute("subnav",subnavigationModel);
    }

    return "/buecher";
  }

}
