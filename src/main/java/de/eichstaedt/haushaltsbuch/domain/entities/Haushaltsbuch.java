package de.eichstaedt.haushaltsbuch.domain.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 */

@Entity
@Table(name = "haushaltsbuecher")
public class Haushaltsbuch {

  protected Haushaltsbuch() {
  }

  public Haushaltsbuch(String name, Benutzer besitzer) {
    this.name = name;
    this.erstellDatum = LocalDate.now();
    this.aenderungsdatum = LocalDate.now();
    this.ausgaben = new HashSet<>();
    this.einnahmen = new HashSet<>();
    this.besitzer = besitzer;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "erstelldatum")
  private LocalDate erstellDatum;

  @Column(name = "aenderungsdatum")
  private LocalDate aenderungsdatum;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "haushaltsbuch_ausgaben" ,joinColumns = @JoinColumn( name="zahlungsfluss_id"),
      inverseJoinColumns = @JoinColumn( name="haushaltsbuecher_id"))
  private Set<EinmaligerZahlungsfluss> ausgaben;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "haushaltsbuch_einnahmen",joinColumns = @JoinColumn( name="zahlungsfluss_id"),
      inverseJoinColumns = @JoinColumn( name="haushaltsbuecher_id"))
  private Set<EinmaligerZahlungsfluss> einnahmen;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "besitzer_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "haushaltsbuch_besitzer_foreignkey"))
  private Benutzer besitzer;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Haushaltsbuch that = (Haushaltsbuch) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Haushaltsbuch{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", erstellDatum=" + erstellDatum +
        ", aenderungsdatum=" + aenderungsdatum +
        ", ausgaben=" + ausgaben +
        ", einnahmen=" + einnahmen +
        ", besitzer=" + besitzer +
        '}';
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getErstellDatum() {
    return erstellDatum;
  }

  public Set<EinmaligerZahlungsfluss> getAusgaben() {
    return ausgaben;
  }

  public Set<EinmaligerZahlungsfluss> getEinnahmen() {
    return einnahmen;
  }

  public Benutzer getBesitzer() {
    return besitzer;
  }

  public LocalDate getAenderungsdatum() {
    return aenderungsdatum;
  }

  public void setAenderungsdatum(LocalDate aenderungsdatum) {
    this.aenderungsdatum = aenderungsdatum;
  }
}
