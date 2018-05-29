package de.eichstaedt.haushaltsbuch.domain.valueobjects;

import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Kategorie kategorie = (Kategorie) o;
    return Objects.equals(name, kategorie.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Kategorie{" +
        "name='" + name + '\'' +
        '}';
  }
}
