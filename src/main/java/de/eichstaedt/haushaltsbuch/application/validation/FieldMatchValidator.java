package de.eichstaedt.haushaltsbuch.application.validation;

import de.eichstaedt.haushaltsbuch.domain.entities.Registrierung;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
    private String firstFieldName;
    private String secondFieldName;
    
    private Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);

    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {   
            final Object firstObj = BeanUtils
                .getPropertyDescriptor(Registrierung.class, firstFieldName).getReadMethod().invoke(value);
            final Object secondObj = BeanUtils
                .getPropertyDescriptor(Registrierung.class, secondFieldName).getReadMethod().invoke(value);
            
            logger.info("Field Match {} for value {} ", firstFieldName,
                BeanUtils.getPropertyDescriptor(Registrierung.class, firstFieldName).getReadMethod().invoke(value));

            if(firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj))
            {
              
              logger.info("Passwort Match first {} second {}",firstObj, secondObj); 
              
              return true;
            }else {
              
              logger.info("Passwort Mismatch first {} second {}",firstObj, secondObj); 
              
              if(Objects.nonNull(context)) {
                  context.buildConstraintViolationWithTemplate(
                      "Passwort Wiederholung stimmt nicht mit Passwort überein!")
                      .addConstraintViolation()
                      .disableDefaultConstraintViolation();
              }
              
              return false;
            }
            
        }
        catch (final Exception e)
        {
            logger.error("Error during validation of passwort",e);
        }
        return true;
    }
}
