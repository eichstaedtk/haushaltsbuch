package de.eichstaedt.haushaltsbuch.domain.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by konrad.eichstaedt@gmx.de on 26.04.18.
 */
public class Haushaltsbuch {

  public Haushaltsbuch(String name, Benutzer besitzer) {
    this.name = name;
    this.erstellDatum = LocalDate.now();
    this.ausgaben = new HashSet<>();
    this.einnahmen = new HashSet<>();
    this.besitzer = besitzer;
  }

  private Long id;

  private String name;

  private LocalDate erstellDatum;

  private Set<Zahlungsfluss> ausgaben;

  private Set<Zahlungsfluss> einnahmen;

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

  public Set<Zahlungsfluss> getAusgaben() {
    return ausgaben;
  }

  public Set<Zahlungsfluss> getEinnahmen() {
    return einnahmen;
  }

  public Benutzer getBesitzer() {
    return besitzer;
  }
}
