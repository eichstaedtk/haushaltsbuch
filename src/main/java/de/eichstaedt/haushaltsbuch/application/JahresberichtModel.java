package de.eichstaedt.haushaltsbuch.application;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.06.18.
 */
public class JahresberichtModel {

  private double[] ausgaben;

  private double[] einnahmen;

  private String titel;

  public JahresberichtModel(double[] ausgaben, double[] einnahmen, String titel) {
    this.ausgaben = ausgaben;
    this.einnahmen = einnahmen;
    this.titel = titel;
  }

  public double[] getAusgaben() {
    return ausgaben;
  }

  public double[] getEinnahmen() {
    return einnahmen;
  }

  public String getTitel() {
    return titel;
  }
}
