package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-05.
 *
 * Das Klasse ermöglicht dem Nutzer Zahlungen für einen Zeitraum und ein Intervall automatisch durch das System durchführen zu lassen.
 */
public class AutomatischeBuchung {

  private Long id;

  private Long haushaltsbuchID;

  private LocalDate startTag;

  private LocalDate endTag;

  private LocalDate letzteBuchungAm;

  private Zahlungsintervall zahlungsintervall;

  private List<String> buchungen;

  private boolean aktiv;

  protected AutomatischeBuchung() {
  }

  public AutomatischeBuchung(Long haushaltsbuchID, LocalDate startTag, LocalDate endTag,
      Zahlungsintervall zahlungsintervall) {
    this.haushaltsbuchID = haushaltsbuchID;
    this.startTag = startTag;
    this.endTag = endTag;
    this.zahlungsintervall = zahlungsintervall;
    this.buchungen = new ArrayList<>();
    this.aktiv = true;
  }

  public Long getId() {
    return id;
  }

  public Long getHaushaltsbuchID() {
    return haushaltsbuchID;
  }

  public LocalDate getStartTag() {
    return startTag;
  }

  public LocalDate getEndTag() {
    return endTag;
  }

  public Zahlungsintervall getZahlungsintervall() {
    return zahlungsintervall;
  }

  public List<String> getBuchungen() {
    return buchungen;
  }

  public boolean isAktiv() {
    return aktiv;
  }

  public void deaktivieren() {
    this.aktiv = false;
  }

  public LocalDate getLetzteBuchungAm() {
    return letzteBuchungAm;
  }

  public boolean istBuchungNotwendig(LocalDate jetzt) {

    if(aktiv && jetzt.isAfter(startTag) && jetzt.isBefore(endTag))
    {

      switch (zahlungsintervall)
      {
        case EINMALIG:
          return false;
        case WOECHENTLICH:
          return letzteBuchungAm.plusDays(7).isAfter(jetzt);
        case MONATLICH:
          return letzteBuchungAm.plusDays(30).isAfter(jetzt);
        case JAEHRLICH:
          return letzteBuchungAm.plusDays(365).isAfter(jetzt);
          default: return false;
      }

    }

    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AutomatischeBuchung that = (AutomatischeBuchung) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
