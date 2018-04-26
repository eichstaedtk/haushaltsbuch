package de.eichstaedt.haushaltsbuch.domain;

import java.util.Objects;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 *
 * Einnahmen sind im kaufmännischen Rechnungswesen, in der Kameralistik und dem Steuerrecht Strömungsgrößen in Form des Zuflusses von Zahlungsmitteln (Bargeld, Buchgeld),
 * der Erhöhung der Forderungen und/oder der Verminderung der Verbindlichkeiten. Komplementärbegriff ist die Ausgabe.
 */
public class Einnahme {

  public Einnahme(Long id, String beschreibung, Double betrag,
      Kategorie kategorie, Zahlungstyp zahlungstyp) {
    this.id = id;
    this.beschreibung = beschreibung;
    this.betrag = betrag;
    this.kategorie = kategorie;
    this.zahlungstyp = zahlungstyp;
  }

  private Long id;

  private String beschreibung;

  private Double betrag;

  private Kategorie kategorie;

  private Zahlungstyp zahlungstyp;

  @Override
  public String toString() {
    return "Einnahme{" +
        "id=" + id +
        ", beschreibung='" + beschreibung + '\'' +
        ", betrag=" + betrag +
        ", kategorie=" + kategorie +
        ", zahlungstyp=" + zahlungstyp +
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
    Einnahme einnahme = (Einnahme) o;
    return Objects.equals(id, einnahme.id);
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

  public Zahlungstyp getZahlungstyp() {
    return zahlungstyp;
  }
}
