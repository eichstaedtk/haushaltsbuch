package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 *
 * Einnahmen sind im kaufmännischen Rechnungswesen, in der Kameralistik und dem Steuerrecht Strömungsgrößen in Form des Zuflusses von Zahlungsmitteln (Bargeld, Buchgeld),
 * der Erhöhung der Forderungen und/oder der Verminderung der Verbindlichkeiten. Komplementärbegriff ist die Ausgabe.
 */

@Entity
@Table(name = "zahlungsfluss")
@Inheritance(
    strategy = InheritanceType.SINGLE_TABLE
)
public class Zahlungsfluss {

  public Zahlungsfluss() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(min = 2, message = "Bitte mindestens zwei Zeichen eingeben!")
  @Column(name = "beschreibung")
  protected String beschreibung;

  @NotNull
  @Column(name = "betrag")
  protected BigDecimal betrag;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "kategorie_name", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "zahlungsfluss_kategorie_foreignkey"))
  protected Kategorie kategorie;


  @NotNull
  @Column(name = "typ")
  @Enumerated(EnumType.STRING)
  protected Zahlungstyp typ;

  @NotNull
  @Column(name = "intervall")
  @Enumerated(EnumType.STRING)
  protected Zahlungsintervall zahlungsintervall;

  @Column(name = "buchid")
  protected Long buchid;

  @Override
  public String toString() {
    return "Zahlungsfluss{" +
        "id=" + id +
        ", beschreibung='" + beschreibung + '\'' +
        ", betrag=" + betrag +
        ", kategorie=" + kategorie +
        ", typ=" + typ +
        ", zahlungsintervall=" + zahlungsintervall +
        ", buchid='" + buchid + '\'' +
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

  public BigDecimal getBetrag() {
    return betrag;
  }

  public Kategorie getKategorie() {
    return kategorie;
  }

  public Zahlungstyp getTyp() {
    return typ;
  }

  public Zahlungsintervall getZahlungsintervall() {
    return zahlungsintervall;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  public void setBetrag(BigDecimal betrag) {
    this.betrag = betrag;
  }

  public void setKategorie(Kategorie kategorie) {
    this.kategorie = kategorie;
  }

  public void setTyp(Zahlungstyp typ) {
    this.typ = typ;
  }

  public void setZahlungsintervall(
      Zahlungsintervall zahlungsintervall) {
    this.zahlungsintervall = zahlungsintervall;
  }

  public Long getBuchid() {
    return buchid;
  }

  public void setBuchid(Long buchid) {
    this.buchid = buchid;
  }
}
