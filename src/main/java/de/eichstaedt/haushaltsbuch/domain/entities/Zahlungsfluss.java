package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

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

  public Zahlungsfluss(
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

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(min = 2, message = "Bitte mindestens zwei Zeichen eingeben!")
  @Column(name = "beschreibung")
  private String beschreibung;

  @NotNull
  @Column(name = "betrag")
  private BigDecimal betrag;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "kategorie_name", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "zahlungsfluss_kategorie_foreignkey"))
  private Kategorie kategorie;


  @NotNull
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "buchungstag")
  private LocalDate buchungsTag;

  @NotNull
  @Column(name = "typ")
  @Enumerated(EnumType.STRING)
  private Zahlungstyp typ;

  @NotNull
  @Column(name = "intervall")
  @Enumerated(EnumType.STRING)
  private Zahlungsintervall zahlungsintervall;

  @Column(name = "buchid")
  private Long buchid;

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

  public LocalDate getBuchungsTag() {
    return buchungsTag;
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

  public Long getBuchid() {
    return buchid;
  }

  public void setBuchid(Long buchid) {
    this.buchid = buchid;
  }
}
