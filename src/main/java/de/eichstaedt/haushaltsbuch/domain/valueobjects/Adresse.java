package de.eichstaedt.haushaltsbuch.domain.valueobjects;

import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@Embeddable
public class Adresse {

  public Adresse(String strasse, String postleitzahl, String wohnort, String land) {
    this.strasse = strasse;
    this.postleitzahl = postleitzahl;
    this.wohnort = wohnort;
    this.land = land;
  }

  private String strasse;

  private String postleitzahl;

  private String wohnort;

  private String land;

  @Override
  public String toString() {
    return "Adresse{" +
        "strasse='" + strasse + '\'' +
        ", postleitzahl='" + postleitzahl + '\'' +
        ", wohnort='" + wohnort + '\'' +
        ", land='" + land + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Adresse adresse = (Adresse) o;
    return Objects.equals(strasse, adresse.strasse) &&
        Objects.equals(postleitzahl, adresse.postleitzahl) &&
        Objects.equals(wohnort, adresse.wohnort) &&
        Objects.equals(land, adresse.land);
  }

  @Override
  public int hashCode() {

    return Objects.hash(strasse, postleitzahl, wohnort, land);
  }

  public String getStrasse() {
    return strasse;
  }

  public String getPostleitzahl() {
    return postleitzahl;
  }

  public String getWohnort() {
    return wohnort;
  }

  public String getLand() {
    return land;
  }
}
