package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zeitraum;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created by konrad.eichstaedt@gmx.de on 20.12.18.
 */

@Entity
public class WiederkehrenderZahlungsfluss extends Zahlungsfluss{

  private int stichtag;

  @NotNull
  @Column(name = "intervall")
  @Enumerated(EnumType.STRING)
  private Zahlungsintervall zahlungsintervall;

  @NotNull
  @Embedded
  private Zeitraum zeitraum;

  public Zahlungsintervall getZahlungsintervall() {
    return zahlungsintervall;
  }

  public int getStichtag() {
    return stichtag;
  }

  public void setStichtag(int stichtag) {
    this.stichtag = stichtag;
  }

  public void setZahlungsintervall(
      Zahlungsintervall zahlungsintervall) {
    this.zahlungsintervall = zahlungsintervall;
  }

  public Zeitraum getZeitraum() {
    return zeitraum;
  }

  public void setZeitraum(Zeitraum zeitraum) {
    this.zeitraum = zeitraum;
  }
}
