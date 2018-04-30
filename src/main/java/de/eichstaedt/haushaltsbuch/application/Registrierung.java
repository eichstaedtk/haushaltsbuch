package de.eichstaedt.haushaltsbuch.application;

import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@Component
public class Registrierung {

  private String vorname;

  private String nachname;

  private String email;

  private String benutzername;

  private String passwort;

  private String passwortWiederholung;

  private String strasse;

  private String stadt;

  private String postleitzahl;

  private String land;

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBenutzername() {
    return benutzername;
  }

  public void setBenutzername(String benutzername) {
    this.benutzername = benutzername;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }

  public String getPasswortWiederholung() {
    return passwortWiederholung;
  }

  public void setPasswortWiederholung(String passwortWiederholung) {
    this.passwortWiederholung = passwortWiederholung;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getStadt() {
    return stadt;
  }

  public void setStadt(String stadt) {
    this.stadt = stadt;
  }

  public String getPostleitzahl() {
    return postleitzahl;
  }

  public void setPostleitzahl(String postleitzahl) {
    this.postleitzahl = postleitzahl;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  @Override
  public String toString() {
    return "Registrierung{" +
        "vorname='" + vorname + '\'' +
        ", nachname='" + nachname + '\'' +
        ", email='" + email + '\'' +
        ", benutzername='" + benutzername + '\'' +
        ", passwort='" + passwort + '\'' +
        ", passwortWiederholung='" + passwortWiederholung + '\'' +
        ", strasse='" + strasse + '\'' +
        ", stadt='" + stadt + '\'' +
        ", postleitzahl='" + postleitzahl + '\'' +
        ", land='" + land + '\'' +
        '}';
  }
}
