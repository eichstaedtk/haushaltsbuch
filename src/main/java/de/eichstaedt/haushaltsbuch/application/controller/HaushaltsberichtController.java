package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.application.model.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.application.model.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.06.18.
 */

@Controller
public class HaushaltsberichtController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsberichtController.class);

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  @Autowired
  private CommonViewController commonViewController;

  @GetMapping("/haushaltsbericht")
  public ModelAndView haushaltsbericht(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "buchid", required = false) String buchid)
  {

    logger.info("Request GET for haushaltsbericht");

    if(buchid != null) {

      JahresberichtModel jahresberichtModel = zahlungsflussBoundaryController
          .createJahresbericht(Long.parseLong(buchid), LocalDate.now().getYear());

      KategorieBerichtModel kategorieBerichtModel = zahlungsflussBoundaryController
          .createJahresKategoriebericht(Long.parseLong(buchid), LocalDate.now().getYear());

      model.addAttribute("ausgaben", jahresberichtModel.getAusgaben());
      model.addAttribute("einnahmen", jahresberichtModel.getEinnahmen());
      model.addAttribute("titel", jahresberichtModel.getTitel());
      model.addAttribute("buch",
          haushaltsbuchBoundaryController.findById(Long.parseLong(buchid)).get());

      model.addAttribute("kategorien", kategorieBerichtModel.getKategorieValues());
      model.addAttribute("kategorientitel", kategorieBerichtModel.getTitel());

      Map<String, String> links = new HashMap<>();

      haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername()).stream()
          .forEach(b -> {
            links.put(b.getName(),
                "/haushaltsbericht?buchid=" + b.getId() + "&active=" + b.getName());
          });

      SubnavigationModel subnavigationModel = new SubnavigationModel("Berichte", links);

      model.addAttribute("subnav", subnavigationModel);

      commonViewController.addHaushaltsbuecherToModel((Model)model,accountDetails.getUsername());
    }

    return new ModelAndView("/haushaltsbericht",model);
  }

}
