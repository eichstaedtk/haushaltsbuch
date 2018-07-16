package de.eichstaedt.haushaltsbuch.domain.controller;

/**
 * Created by konrad.eichstaedt@gmx.de on 16.07.18.
 */
public class HaushaltbuchLoeschenFailedException extends Exception {

  public HaushaltbuchLoeschenFailedException() {
  }

  public HaushaltbuchLoeschenFailedException(String message) {
    super(message);
  }

  public HaushaltbuchLoeschenFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  public HaushaltbuchLoeschenFailedException(Throwable cause) {
    super(cause);
  }

  public HaushaltbuchLoeschenFailedException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
