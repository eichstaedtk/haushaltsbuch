package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * Created by konrad.eichstaedt@gmx.de on 09.05.18.
 */

@Controller
@Component(value = "dashboardController")
public class DashboardController {

  private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  private Haushaltsbuch selectedHaushaltsbuch;


  @GetMapping(value = "dashboard")
  public String registration(Model model,@AuthenticationPrincipal User accountDetails, @RequestParam(defaultValue = "off") boolean neueshaushaltsbuch) {

    logger.info("GET Request for dashboard page {} ", neueshaushaltsbuch);

    model.addAttribute("user",accountDetails);

    model.addAttribute("neueshaushaltsbuch",neueshaushaltsbuch);

    List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername());

    Optional<Haushaltsbuch> latest = haushaltsbuecher.stream().sorted((h1, h2) -> h2.getId().compareTo(h1.getId())).findFirst();

    if(Objects.nonNull(haushaltsbuecher)) {
      model.addAttribute("haushaltsbuecher", haushaltsbuecher);
    }

    if(latest.isPresent())
    {
      selectedHaushaltsbuch = latest.get();

      model.addAttribute("selectedHaushaltsbuch", selectedHaushaltsbuch);
      logger.info("Setting selected haushaltsbuch to {} ", selectedHaushaltsbuch);
    }

    if(neueshaushaltsbuch)
    {
      model.addAttribute("defaultview",false);

      logger.info("Setting dashboard for new haushaltsbuch page");

    }else {

      model.addAttribute("defaultview",true);

      logger.info("Setting dashboard for default page");
    }



    return "/dashboard";
  }
}
