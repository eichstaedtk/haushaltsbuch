package de.eichstaedt.haushaltsbuch.application;

import java.util.Arrays;

/**
 * Created by konrad.eichstaedt@gmx.de on 10.06.18.
 */
public class KategorieBerichtModel {

  private Object [][] kategorieValues;

  private String titel;

  public KategorieBerichtModel(Object[][] kategorieValues, String titel) {
    this.kategorieValues = kategorieValues;
    this.titel = titel;
  }

  public Object[][] getKategorieValues() {
    return kategorieValues;
  }

  public String getTitel() {
    return titel;
  }

  @Override
  public String toString() {
    return "KategorieBerichtModel{" +
        "kategorieValues=" + Arrays.toString(kategorieValues) +
        ", titel='" + titel + '\'' +
        '}';
  }
}
