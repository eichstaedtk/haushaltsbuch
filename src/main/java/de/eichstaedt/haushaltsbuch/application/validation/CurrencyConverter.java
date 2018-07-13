package de.eichstaedt.haushaltsbuch.application.validation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by konrad.eichstaedt@gmx.de on 11.06.18.
 */


public class CurrencyConverter implements Converter<String,BigDecimal> {

  private Logger logger = LoggerFactory.getLogger(CurrencyConverter.class);

  @Override
  public BigDecimal convert(String source) {

    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.');
    symbols.setDecimalSeparator(',');
    String pattern = "#.###,##";
    DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
    decimalFormat.setParseBigDecimal(true);

    try {

      return (BigDecimal) decimalFormat.parse(source);
    } catch (ParseException e) {
      logger.error("Error parsing currency",e);
      return null;
    }
  }
}
