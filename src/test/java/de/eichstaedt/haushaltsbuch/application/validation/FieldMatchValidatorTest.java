package de.eichstaedt.haushaltsbuch.application.validation;

import static org.hamcrest.Matchers.is;

import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by konrad.eichstaedt@gmx.de on 15.07.18.
 */
public class FieldMatchValidatorTest {

  @Test
  public void testisValid() {


    FieldMatchValidator validator = new FieldMatchValidator();

    FieldMatch constraintAnnotation = Mockito.mock(FieldMatch.class);

    Mockito.when(constraintAnnotation.first()).thenReturn("passwort");
    Mockito.when(constraintAnnotation.second()).thenReturn("passwortWiederholung");

    validator.initialize(constraintAnnotation);

    Registrierung registrierung = new Registrierung.RegistrierungBuilder("konrad.eichstaedt@gmx.de","konrad123","Start123","Start123")
        .withName("Konrad","Eichstädt")
        .build();

    Assert.assertThat(validator.isValid(registrierung,null ),is(true));

    Registrierung registrierungWrong = new Registrierung.RegistrierungBuilder("konrad.eichstaedt@gmx.de","konrad123","Start123","Start12345")
        .withName("Konrad","Eichstädt")
        .build();

    Assert.assertThat(validator.isValid(registrierungWrong,null ),is(false));

  }
}
