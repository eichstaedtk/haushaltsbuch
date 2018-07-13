package de.eichstaedt.haushaltsbuch.application.validation;

import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BenutzernameValidator implements ConstraintValidator<BenutzernameAlreadyUsed, String> {

  private BenutzernameAlreadyUsed annotation;

  private static final Logger logger = LoggerFactory.getLogger(BenutzernameValidator.class);

  @Autowired
  public BenutzernameValidator(
      BenutzerBoundaryController benutzerBoundaryController) {
    this.benutzerBoundaryController = benutzerBoundaryController;
  }

  private BenutzerBoundaryController benutzerBoundaryController;
  
  @Override
  public void initialize(BenutzernameAlreadyUsed used) {
    annotation = used;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if(Objects.nonNull(value) && Objects.nonNull(benutzerBoundaryController))
    {

      logger.info("Check Benutzername {} " ,value);

      return benutzerBoundaryController.isBenutzernameFree(value);
    }

    return false;
  }

}
