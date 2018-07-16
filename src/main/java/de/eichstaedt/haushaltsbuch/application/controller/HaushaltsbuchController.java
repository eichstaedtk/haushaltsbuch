package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.application.model.Pager;
import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltbuchLoeschenFailedException;
import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by konrad.eichstaedt@gmx.de on 19.05.18.
 */

@Controller
public class HaushaltsbuchController {

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchController.class);

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  @Autowired
  private ZahlungsflussBoundaryController zahlungsflussBoundaryController;

  @Autowired
  private KategorieBoundaryController kategorieBoundaryController;

  private Zahlungsfluss neueZahlung;

  private static final int BUTTONS_TO_SHOW = 5;
  private static final int INITIAL_PAGE = 0;
  private static final int INITIAL_PAGE_SIZE = 5;
  private static final int[] PAGE_SIZES = {5, 10, 20};

  @PostMapping("/haushaltsbuch")
  public ModelAndView neuesHaushaltsbuch(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "name") String haushaltsbuchName) {

    logger.info("Getting POST request for creating neues haushalstbuch {} ", haushaltsbuchName);

    model.addAttribute("user",accountDetails);

    Haushaltsbuch haushaltsbuch = haushaltsbuchBoundaryController
        .createHaushaltsbuch(haushaltsbuchName,accountDetails.getUsername());

    logger.info("Neues Haushaltsbuch erstellt {} ", haushaltsbuch);

    return new ModelAndView("redirect:/dashboard",model);
  }

  @GetMapping("/haushaltsbuch")
  public ModelAndView oeffnen(ModelMap model,@AuthenticationPrincipal User accountDetails, @RequestParam(value = "buchid") String buchid,
      @RequestParam(value = "zahlungsid",required = false) String zahlungsid, @RequestParam("pageSize") Optional<Integer> pageSize,
      @RequestParam("page") Optional<Integer> page) {

    logger.info("Getting GET request for oeffen haushalstbuch {} ", buchid);

    List<Haushaltsbuch> buecher = haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername());

    Optional<Haushaltsbuch> buch = buecher.stream().filter(b -> String.valueOf(b.getId()).equals(buchid)).findFirst();

    if(buch.isPresent()) {

      logger.info("Found buch with id {} buch {} ", buchid,buch);

      if(Objects.nonNull(zahlungsid) && !zahlungsid.isEmpty() && zahlungsflussBoundaryController.laden(zahlungsid).isPresent() )
      {
          neueZahlung = zahlungsflussBoundaryController.laden(zahlungsid).get();

      }else {

        neueZahlung = new Zahlungsfluss();
      }

      if(model.containsAttribute("neuezahlung"))
      {
        neueZahlung = (Zahlungsfluss) model.get("neuezahlung");
      }

      int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
      // Evaluate page. If requested parameter is null or less than 0 (to
      // prevent exception), return initial size. Otherwise, return value of
      // param. decreased by 1.
      int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;


      Page<Zahlungsfluss> zahlungen = zahlungsflussBoundaryController.findAllPageable(PageRequest.of(evalPage, evalPageSize,Sort.by(
          new Order(Direction.DESC, "buchungsTag"))),buch.get().getId());
      Pager pager = new Pager(zahlungen.getTotalPages(),zahlungen.getNumber(), BUTTONS_TO_SHOW);

      model.addAttribute("buch",buch.get());
      model.addAttribute("neuezahlung",neueZahlung);
      model.addAttribute("allkategories",kategorieBoundaryController.findAll());
      model.addAttribute("allzahlungstypen",Zahlungstyp.values());
      model.addAttribute("allzahlungsintervalle",Zahlungsintervall.values());


      model.addAttribute("selectedPageSize", evalPageSize);
      model.addAttribute("pageSizes", PAGE_SIZES);
      model.addAttribute("pager", pager);
      model.addAttribute("zahlungen", zahlungen);

    }

    return new ModelAndView("/haushaltsbuch",model);
  }

  @GetMapping("/haushaltsbuch/{buchid}/loeschen")
  public ModelAndView haushltsbuchLoeschen(ModelMap model,@AuthenticationPrincipal User accountDetails, @PathVariable String buchid, RedirectAttributes redirectAttributes) {

    logger.info("Getting request for haushaltsbuch loeschen for buch {} ", buchid);

    try {

      haushaltsbuchBoundaryController.loeschen(Long.valueOf(buchid), accountDetails.getUsername());

    } catch (HaushaltbuchLoeschenFailedException e) {
      logger.error("Loeschen for haushaltsbuch failed", e);

      redirectAttributes.addFlashAttribute("errorloeschenbuch",e.getLocalizedMessage());
    }

    List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController.findAllHaushaltsbuecher(accountDetails.getUsername());

    model.addAttribute("haushaltsbuecher", haushaltsbuecher);

    return new ModelAndView("redirect:/dashboard",model);
  }

  public Zahlungsfluss getNeueZahlung() {
    return neueZahlung;
  }
}
