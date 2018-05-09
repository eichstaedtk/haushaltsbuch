package de.eichstaedt.haushaltsbuch.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.05.18.
 */

@Controller
public class DashboardController {

  private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

  @GetMapping(value = "dashboard")
  public String registration(Model model,@AuthenticationPrincipal User accountDetails) {

    logger.info("GET Request for registration page");

    model.addAttribute("user",accountDetails);

    return "/dashboard";
  }

}
