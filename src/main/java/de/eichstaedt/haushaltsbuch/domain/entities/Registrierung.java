package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.application.BenutzernameAlreadyUsed;
import de.eichstaedt.haushaltsbuch.application.FieldMatch;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.List;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@FieldMatch.List({@FieldMatch(first = "passwort", second = "passwortWiederholung",
    message = "Passwort Wiederholung stimmt nicht mit Passwort überein!"),})
@Component
public class Registrierung {

  public Registrierung() {
  }

  public Registrierung(
      @NotNull @Size(min = 2, max = 55, message = "Bitte geben Sie mindestens 2 maximal 55 Zeichen ein!") @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!") String benutzername,
      @NotNull @Pattern(regexp = ".*@.*\\.[a-z|A-Z]{1,3}$",
          message = "Bitte eine gültige E-Mail-Adresse verwenden!") String email,
      @NotNull @Size(min = 6, max = 55, message = "Bitte geben Sie mindestens 6 maximal 55 Zeichen ein!") @List({
          @Pattern(regexp = "^(?=.*[A-Z]).+$",
              message = "Bitte mindestens einen großen Buchstaben angeben!"),
          @Pattern(regexp = "^(?=.*[a-z]).+$",
              message = "Bitte mindestens einen kleine Buchstaben angeben!"),
          @Pattern(regexp = "^(?=.*\\d).+$", message = "Bitte mindestens eine Zahl angeben!"),
          @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")}) String passwort) {
    this.email = email;
    this.benutzername = benutzername;
    this.passwort = passwort;
  }

  @NotNull
  @Size(min = 2, max = 55, message = "Bitte geben Sie mindestens 2 maximal 55 Zeichen ein!")
  @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")
  private String vorname;

  @NotNull
  @Size(min = 2, max = 55, message = "Bitte geben Sie mindestens 2 maximal 55 Zeichen ein!")
  @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")
  private String nachname;

  @NotNull
  @Pattern(regexp = ".*@.*\\.[a-z|A-Z]{1,3}$",
      message = "Bitte eine gültige E-Mail-Adresse verwenden!")
  private String email;

  @NotNull
  @Size(min = 2, max = 55, message = "Bitte geben Sie mindestens 2 maximal 55 Zeichen ein!")
  @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")
  @BenutzernameAlreadyUsed()
  private String benutzername;

  @NotNull
  @Size(min = 6, max = 55, message = "Bitte geben Sie mindestens 6 maximal 55 Zeichen ein!")
  @List({
      @Pattern(regexp = "^(?=.*[A-Z]).+$",
          message = "Bitte mindestens einen großen Buchstaben angeben!"),
      @Pattern(regexp = "^(?=.*[a-z]).+$",
          message = "Bitte mindestens einen kleine Buchstaben angeben!"),
      @Pattern(regexp = "^(?=.*\\d).+$", message = "Bitte mindestens eine Zahl angeben!"),
      @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")})
  private String passwort;

  @NotNull
  @Size(min = 6, max = 55, message = "Bitte geben Sie mindestens 6 maximal 55 Zeichen ein!")
  @List({
      @Pattern(regexp = "^(?=.*[A-Z]).+$",
          message = "Bitte mindestens einen großen Buchstaben angeben!"),
      @Pattern(regexp = "^(?=.*[a-z]).+$",
          message = "Bitte mindestens einen kleine Buchstaben angeben!"),
      @Pattern(regexp = "^(?=.*\\d).+$", message = "Bitte mindestens eine Zahl angeben!"),
      @Pattern(regexp = "^[^\\s]+$", message = "Bitte keine Leerzeichen verwenden!")})
  private String passwortWiederholung;

  private String strasse;

  private String stadt;

  private String postleitzahl;

  private String land;

  public Registrierung(RegistrierungBuilder registrierungBuilder) {
    this.vorname = registrierungBuilder.vorname;
    this.nachname = registrierungBuilder.nachname;
    this.benutzername = registrierungBuilder.benutzername;
    this.passwort = registrierungBuilder.passwort;
    this.passwortWiederholung = registrierungBuilder.passwortWiederholung;
    this.email = registrierungBuilder.email;
    this.strasse = registrierungBuilder.strasse;
    this.stadt = registrierungBuilder.stadt;
    this.postleitzahl = registrierungBuilder.postleitzahl;
    this.land = registrierungBuilder.land;
  }

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

  public static class RegistrierungBuilder {

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

    public RegistrierungBuilder(String email, String benutzername, String passwort,
        String passwortWiederholung) {
      this.email = email;
      this.benutzername = benutzername;
      this.passwort = passwort;
      this.passwortWiederholung = passwortWiederholung;
    }

    public RegistrierungBuilder withName(String vorname, String nachname)
    {
      this.vorname = vorname;
      this.nachname = nachname;

      return this;
    }

    public RegistrierungBuilder withAdresse(String stadt, String strasse, String postleitzahl, String land) {

      this.stadt = stadt;
      this.strasse = strasse;
      this.postleitzahl = postleitzahl;
      this.land = land;

      return this;
    }

    public Registrierung build() {
      return new Registrierung(this);
    }
  }
}
