package de.eichstaedt.haushaltsbuch.domain.valueobjects;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 */
public enum Zahlungsintervall {

  EINMALIG,WOECHENTLICH,MONATLICH,JAEHRLICH;

  public String toString() {
    switch (this)
    {

      case EINMALIG:
        return "einmalig";
      case WOECHENTLICH:
        return "wöchentlich";
      case MONATLICH:
        return "monatlich";
      case JAEHRLICH:return "jährlich";
      default:return "";
    }
  }

}
