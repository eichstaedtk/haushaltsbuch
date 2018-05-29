package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.repository.KategorieRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 29.05.18.
 */

@Component
public class KategorieService implements KategorieBoundaryController {

  @Autowired
  public KategorieService(
      KategorieRepository kategorieRepository) {
    this.kategorieRepository = kategorieRepository;
  }

  private static final Logger logger = LoggerFactory.getLogger(KategorieBoundaryController.class);

  private KategorieRepository kategorieRepository;

  @Override
  public void createDefaultKategories() {

    logger.info("Creating the default kategories");

    List<Kategorie> kategories = Arrays.asList(new Kategorie("Gehalt"),new Kategorie("Lebensmittel"),new Kategorie("Freizeit"),new Kategorie("Versicherung"),new Kategorie("Haus"));

    kategorieRepository.saveAll(kategories);

  }

  @Override
  public List<Kategorie> findAll() {

    logger.info("Finding all kategories");

    List<Kategorie> result = new ArrayList();

    kategorieRepository.findAll().forEach(result::add);

    return result;
  }
}
