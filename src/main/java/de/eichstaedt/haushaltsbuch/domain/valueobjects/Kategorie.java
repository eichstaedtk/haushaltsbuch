package de.eichstaedt.haushaltsbuch.domain.valueobjects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 */

@Entity
public class Kategorie {

  protected Kategorie() {
  }

  public Kategorie(String name) {
    this.name = name;
  }

  @Id
  private String name;

  public String getName() {
    return name;
  }
}
