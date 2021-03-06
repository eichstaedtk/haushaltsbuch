package de.eichstaedt.haushaltsbuch.domain.entities;

import de.eichstaedt.haushaltsbuch.domain.valueobjects.Adresse;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by konrad.eichstaedt@gmx.de on 28.04.18.
 */

@Entity
@Table(name = "Benutzer")
public class Benutzer {

  protected Benutzer() {
  }

  public Benutzer(BenutzerBuilder builder) {

    this.benutzername = builder.benutzername;
    this.vorname = builder.vorname;
    this.nachname = builder.nachname;
    this.email = builder.email;
    this.wohnort = builder.wohnort;
    this.passwort = builder.passwort;
    this.aktiviert = builder.aktiviert;
    this.aktivierungBis = builder.aktivierungBis;
    this.aktivierungsCode = builder.aktivierungsCode;

  }

  @Id
  private String benutzername;

  @Column(name = "vorname", length = 55)
  private String vorname;

  @Column(name = "nachname", length = 55)
  private String nachname;

  @Column(name = "email", length = 55)
  private String email;

  @Column(name = "passwort")
  private String passwort;

  @Embedded
  private Adresse wohnort;

  @Column(name = "aktiviert")
  private boolean aktiviert;

  @Column(name = "aktivierungBis")
  private LocalDateTime aktivierungBis;

  private String aktivierungsCode;

  public LocalDateTime getAktivierungBis() {
    return aktivierungBis;
  }

  public String getAktivierungsCode() {
    return aktivierungsCode;
  }

  public Adresse getWohnort() {
    return wohnort;
  }

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

  public boolean isAktiviert() {
    return aktiviert;
  }

  public void aktivieren() {
    this.aktiviert = true;
    this.aktivierungsCode = null;
    this.aktivierungBis = null;
  }

  public static class BenutzerBuilder {

    public BenutzerBuilder(String benutzername, String email, String passwort,
        PasswordEncoder passwordEncoder) {
      this.benutzername = benutzername;
      this.email = email;
      this.passwort = passwordEncoder.encode(passwort);
      this.aktiviert = false;
      this.aktivierungBis = LocalDateTime.now().plusDays(7L);
      this.aktivierungsCode = UUID.randomUUID().toString();
    }

    private String benutzername;

    private String vorname;

    private String nachname;

    private String email;

    private String passwort;

    private Adresse wohnort;

    private boolean aktiviert;

    private LocalDateTime aktivierungBis;

    private String aktivierungsCode;

    public BenutzerBuilder withWohnort(String strasse, String postleitzahl, String stadt,
        String land) {

      Adresse adresse = new Adresse(strasse, postleitzahl, stadt, land);

      this.wohnort = adresse;

      return this;
    }

    public BenutzerBuilder withName(String vorname, String nachname) {
      this.vorname = vorname;
      this.nachname = nachname;

      return this;
    }

    public Benutzer build() {
      return new Benutzer(this);
    }

  }

  @Override
  public String toString() {
    return "Benutzer{" +
        "benutzername='" + benutzername + '\'' +
        ", vorname='" + vorname + '\'' +
        ", nachname='" + nachname + '\'' +
        ", email='" + email + '\'' +
        ", passwort='" + passwort + '\'' +
        ", wohnort=" + wohnort +
        ", aktiviert=" + aktiviert +
        ", aktivierungBis=" + aktivierungBis +
        ", aktivierungsCode='" + aktivierungsCode + '\'' +
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
