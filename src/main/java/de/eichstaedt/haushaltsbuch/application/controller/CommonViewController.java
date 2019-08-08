package de.eichstaedt.haushaltsbuch.application.controller;

import de.eichstaedt.haushaltsbuch.domain.controller.HaushaltsbuchBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Created by konrad.eichstaedt@gmx.de on 2019-08-08.
 */

@Component
public class CommonViewController {

  @Autowired
  private HaushaltsbuchBoundaryController haushaltsbuchBoundaryController;

  public Model addHaushaltsbuecherToModel(Model model, String benutzerName) {

    List<Haushaltsbuch> haushaltsbuecher = haushaltsbuchBoundaryController
        .findAllHaushaltsbuecher(benutzerName);

    if (Objects.nonNull(haushaltsbuecher)) {
      model.addAttribute("haushaltsbuecher", haushaltsbuecher);
    }

    return model;

  }

}
