package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.repository.AutomatischeBuchungRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.ZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungsintervall;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by konrad.eichstaedt@gmx.de on 2020-03-05.
 *
 * Das Klasse ermöglicht dem Nutzer Zahlungen für einen Zeitraum und ein Intervall automatisch durch das System durchführen zu lassen.
 */

@Entity
@Table(name = "automatische_buchungen")
public class AutomatischeBuchung {

  private static final Logger logger = LoggerFactory.getLogger(AutomatischeBuchung.class);

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "start_tag")
  private LocalDate startTag;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "end_tag")
  private LocalDate endTag;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "letzteBuchungAm")
  private LocalDate letzteBuchungAm;

  @ElementCollection
  @Column(name = "buchungen")
  private Set<Long> buchungen;

  @Column(name = "aktiv")
  private boolean aktiv;

  @OneToOne(fetch = FetchType.EAGER)
  private Zahlungsfluss zahlung;

  @NotNull
  @Column(name = "intervall")
  @Enumerated(EnumType.STRING)
  private Zahlungsintervall zahlungsintervall;

  protected AutomatischeBuchung() {
  }


  public AutomatischeBuchung(LocalDate startTag, LocalDate endTag,
      Zahlungsfluss zahlung,Zahlungsintervall zahlungsintervall) {
    this.startTag = startTag;
    this.endTag = endTag;
    this.buchungen = new HashSet<>();
    this.aktiv = true;
    this.zahlung = zahlung;
    this.zahlungsintervall = zahlungsintervall;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getStartTag() {
    return startTag;
  }

  public LocalDate getEndTag() {
    return endTag;
  }


  public Set<Long> getBuchungen() {
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

  public Zahlungsfluss getZahlung() {
    return zahlung;
  }

  public Zahlungsintervall getZahlungsintervall() {
    return zahlungsintervall;
  }

  public void setZahlungsintervall(
      Zahlungsintervall zahlungsintervall) {
    this.zahlungsintervall = zahlungsintervall;
  }

  public void automatischBuchen(ZahlungsflussRepository zahlungsflussRepository,AutomatischeBuchungRepository automatischeBuchungRepository) {

    if(aktiv && istBuchungNotwendig(LocalDate.now())) {

      logger.info("Starting new automatic booki");

      Zahlungsfluss neueZahlung = this.zahlung.clone();
      neueZahlung.setBuchungsTag(LocalDate.now());

      neueZahlung = zahlungsflussRepository.save(neueZahlung);

      this.letzteBuchungAm = neueZahlung.getBuchungsTag();

      this.buchungen.add(neueZahlung.getId());

      speichern(automatischeBuchungRepository);
    }

  }

  public AutomatischeBuchung speichern(AutomatischeBuchungRepository automatischeBuchungRepository) {
    return automatischeBuchungRepository.save(this);
  }

  boolean istBuchungNotwendig(LocalDate jetzt) {

    if(aktiv && jetzt.isAfter(startTag) && jetzt.isBefore(endTag) && zahlung != null)
    {

      if(letzteBuchungAm == null)
        return true;

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
