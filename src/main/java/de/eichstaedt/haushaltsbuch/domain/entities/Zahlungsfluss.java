package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 *
 * Einnahmen sind im kaufmännischen Rechnungswesen, in der Kameralistik und dem Steuerrecht Strömungsgrößen in Form des Zuflusses von Zahlungsmitteln (Bargeld, Buchgeld),
 * der Erhöhung der Forderungen und/oder der Verminderung der Verbindlichkeiten. Komplementärbegriff ist die Ausgabe.
 */

public class Zahlungsfluss {

  private Long id;

  private String beschreibung;

  private Double betrag;

  private Kategorie kategorie;

  private LocalDate buchungsTag;

  private Zahlungstyp typ;

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
}
