package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 *
 * Einnahmen sind im kaufmännischen Rechnungswesen, in der Kameralistik und dem Steuerrecht Strömungsgrößen in Form des Zuflusses von Zahlungsmitteln (Bargeld, Buchgeld),
 * der Erhöhung der Forderungen und/oder der Verminderung der Verbindlichkeiten. Komplementärbegriff ist die Ausgabe.
 */

@Entity
@Table(name = "zahlungsfluss")
public class Zahlungsfluss {

  public Zahlungsfluss() {
  }

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "beschreibung")
  private String beschreibung;

  @Column(name = "betrag")
  private Double betrag;

  @Transient
  private Kategorie kategorie;

  @Column(name = "buchungstag")
  private LocalDate buchungsTag;

  @Column(name = "typ")
  @Enumerated(EnumType.STRING)
  private Zahlungstyp typ;

  @Column(name = "intervall")
  @Enumerated(EnumType.STRING)
  private Zahlungsintervall zahlungsintervall;

  @Override
  public String toString() {
    return "Zahlungsfluss{" +
        "id=" + id +
        ", beschreibung='" + beschreibung + '\'' +
        ", betrag=" + betrag +
        ", kategorie=" + kategorie +
        ", buchungsTag=" + buchungsTag +
        ", typ=" + typ +
        ", zahlungsintervall=" + zahlungsintervall +
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
    Zahlungsfluss zahlungsfluss = (Zahlungsfluss) o;
    return Objects.equals(id, zahlungsfluss.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  public Long getId() {
    return id;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public Double getBetrag() {
    return betrag;
  }

  public Kategorie getKategorie() {
    return kategorie;
  }

  public LocalDate getBuchungsTag() {
    return buchungsTag;
  }

  public Zahlungstyp getTyp() {
    return typ;
  }

  public Zahlungsintervall getZahlungsintervall() {
    return zahlungsintervall;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  public void setBetrag(Double betrag) {
    this.betrag = betrag;
  }

  public void setKategorie(Kategorie kategorie) {
    this.kategorie = kategorie;
  }

  public void setBuchungsTag(LocalDate buchungsTag) {
    this.buchungsTag = buchungsTag;
  }

  public void setTyp(Zahlungstyp typ) {
    this.typ = typ;
  }

  public void setZahlungsintervall(
      Zahlungsintervall zahlungsintervall) {
    this.zahlungsintervall = zahlungsintervall;
  }
}
