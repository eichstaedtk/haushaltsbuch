package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Autowired
  private CommonViewController commonViewController;

  @PostMapping("/kategorien")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String kategorienname) {

    logger.info("Getting POST request for creating neue Kategorie {} ", kategorienname);

    kategorieBoundaryController.createKategorie(kategorienname);

    return new ModelAndView("redirect:/kategorien",model);
  }

  @GetMapping("/kategorien")
  public ModelAndView kategorien(ModelMap model,@RequestParam(defaultValue = "Alle Kategorien") String active,@AuthenticationPrincipal User accountDetails) {

    List<Kategorie> kategorien = kategorieBoundaryController.findAll();

    if(Objects.nonNull(kategorien)) {
      model.addAttribute("kategorien", kategorien);
    }

    Map<String,String> links = new HashMap<>();
    links.put("Alle Kategorien","/kategorien?active=Alle Kategorien");
    links.put("Neue Kategorie erstellen","/kategorien?active=Neue Kategorie erstellen");


    SubnavigationModel subnavigationModel = new SubnavigationModel("Kategorien", links);
    subnavigationModel.setActiveItem(active);

    model.addAttribute("subnav",subnavigationModel);

    commonViewController.addHaushaltsbuecherToModel((Model)model,accountDetails.getUsername());

    return new ModelAndView("/kategorien",model);
  }

}
