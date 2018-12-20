package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by konrad.eichstaedt@gmx.de on 20.12.18.
 */

@Entity
public class EinmaligeZahlung extends Zahlungsfluss {

  public EinmaligeZahlung() {
  }

  @NotNull
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "buchungstag")
  private LocalDate buchungsTag;

  public EinmaligeZahlung(
      String beschreibung,
      BigDecimal betrag,
      Kategorie kategorie,
      LocalDate buchungsTag,
      Zahlungstyp typ,
      Zahlungsintervall zahlungsintervall, Long buchid) {
    this.beschreibung = beschreibung;
    this.betrag = betrag;
    this.kategorie = kategorie;
    this.buchungsTag = buchungsTag;
    this.typ = typ;
    this.zahlungsintervall = zahlungsintervall;
    this.buchid = buchid;
  }

  public LocalDate getBuchungsTag() {
    return buchungsTag;
  }

  public void setBuchungsTag(LocalDate buchungsTag) {
    this.buchungsTag = buchungsTag;
  }
}
