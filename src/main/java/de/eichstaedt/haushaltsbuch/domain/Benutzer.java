package de.eichstaedt.haushaltsbuch.domain;

import java.util.Objects;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */
public class Benutzer {

  private String benutzername;

  private String vorname;

  private String nachname;

  private String email;

  private String passwort;

  public String getBenutzername() {
    return benutzername;
  }

  public String getVorname() {
    return vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public String getEmail() {
    return email;
  }

  public String getPasswort() {
    return passwort;
  }

  @Override
  public String toString() {
    return "Benutzer{" +
        "benutzername='" + benutzername + '\'' +
        ", vorname='" + vorname + '\'' +
        ", nachname='" + nachname + '\'' +
        ", email='" + email + '\'' +
        ", passwort='" + passwort + '\'' +
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
    Benutzer benutzer = (Benutzer) o;
    return Objects.equals(benutzername, benutzer.benutzername);
  }

  @Override
  public int hashCode() {

    return Objects.hash(benutzername);
  }
}
