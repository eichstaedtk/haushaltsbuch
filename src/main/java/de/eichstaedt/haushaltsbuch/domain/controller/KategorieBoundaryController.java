package de.eichstaedt.haushaltsbuch.domain.controller;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import java.util.List;

/**
 * Created by konrad.eichstaedt@gmx.de on 29.05.18.
 */
public interface KategorieBoundaryController {

  void createDefaultKategories();

  List<Kategorie> findAll();

}
