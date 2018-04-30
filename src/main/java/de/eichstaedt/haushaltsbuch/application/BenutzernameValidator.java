package de.eichstaedt.haushaltsbuch.application;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BenutzernameValidator implements ConstraintValidator<BenutzernameAlreadyUsed, String> {

  private BenutzernameAlreadyUsed annotation;
  
  @Override
  public void initialize(BenutzernameAlreadyUsed used) {
    annotation = used;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {


    return false;
  }

}
