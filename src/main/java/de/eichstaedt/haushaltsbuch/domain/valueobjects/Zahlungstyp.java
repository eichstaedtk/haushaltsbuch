package de.eichstaedt.haushaltsbuch.domain.valueobjects;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 */
public enum Zahlungstyp {

  AUSGABE,EINNAHME;

  @Override
  public String toString() {

    switch(this)
    {
      case AUSGABE:
        return "Ausgabe";
      case EINNAHME:
        return "Einnahme";
      default: return "";
    }

  }


}
