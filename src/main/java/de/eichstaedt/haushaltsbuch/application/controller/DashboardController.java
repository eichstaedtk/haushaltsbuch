package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.application.model.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.application.model.SubnavigationModel;
import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
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

  @Autowired
  private CommonViewController commonViewController;

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  private int aktiverMonat;

  @GetMapping(value = "/dashboard")
  public String dashboard(Model model,@AuthenticationPrincipal User accountDetails, @RequestParam(defaultValue = "off") boolean neueshaushaltsbuch, @RequestParam(value = "monat",required = false)Integer monat) {

    logger.info("GET Request for dashboard page {} ", neueshaushaltsbuch);

    model.addAttribute("user",accountDetails);

    commonViewController.addHaushaltsbuecherToModel(model,accountDetails.getUsername());

    List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername());

    if(Objects.nonNull(haushaltsbuecher)) {
      Optional<Haushaltsbuch> latest = haushaltsbuecher.stream().sorted((h1, h2) -> h2.getId().compareTo(h1.getId())).findFirst();

      if(latest.isPresent())
      {
        selectedHaushaltsbuch = latest.get();

        model.addAttribute("selectedHaushaltsbuch", selectedHaushaltsbuch);


        if(monat == null)
        {
          monat = LocalDate.now().getMonthValue();
        }

        aktiverMonat = monat;
        model.addAttribute("aktiverMonat",aktiverMonat);

        KategorieBerichtModel kategorieBerichtModel = zahlungsflussBoundaryController
            .createJahresKategoriebericht(selectedHaushaltsbuch.getId(), LocalDate.now().getYear(),monat);

        model.addAttribute("kategorien", kategorieBerichtModel.getKategorieValues());
        model.addAttribute("kategorientitel", kategorieBerichtModel.getTitel());

        logger.info("Setting selected haushaltsbuch to {} ", selectedHaushaltsbuch);
      }
    }


    SubnavigationModel subnavigationModel = new SubnavigationModel("Dashboard", new HashMap<>());

    model.addAttribute("subnav",subnavigationModel);

    model.addAttribute("monate",createMonthMap());

    return "/dashboard";
  }

  private HashMap<Integer, String> createMonthMap() {
    HashMap<Integer,String> monthsMap = new HashMap<>();
    List<String> months = Arrays
        .asList("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember");
    for(int i = 1; i<13;i++)
    {
      monthsMap.put(i,months.get(i-1));
    }
    return monthsMap;
  }
}
