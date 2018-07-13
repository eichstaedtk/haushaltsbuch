package de.eichstaedt.haushaltsbuch.application.model;

import java.math.BigDecimal;

/**
 * Created by konrad.eichstaedt@gmx.de on 09.06.18.
 */
public class JahresberichtModel {

  private BigDecimal[] ausgaben;

  private BigDecimal[] einnahmen;

  private String titel;

  public JahresberichtModel(BigDecimal[] ausgaben, BigDecimal[] einnahmen, String titel) {
    this.ausgaben = ausgaben;
    this.einnahmen = einnahmen;
    this.titel = titel;
  }

  public BigDecimal[] getAusgaben() {
    return ausgaben;
  }

  public BigDecimal[] getEinnahmen() {
    return einnahmen;
  }

  public String getTitel() {
    return titel;
  }
}
