package de.eichstaedt.haushaltsbuch.domain.valueobjects;

import java.time.LocalDate;

/**
 * Created by konrad.eichstaedt@gmx.de on 20.12.18.
 */
public class Zeitraum {

  private LocalDate startDate;

  private LocalDate endDate;

  public Zeitraum(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public String toString() {
    return "Zeitraum{" +
        "startDate=" + startDate +
        ", endDate=" + endDate +
        '}';
  }
}
