package de.eichstaedt.haushaltsbuch.infrastructure;

import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import de.eichstaedt.haushaltsbuch.domain.repository.BenutzerRepository;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@Component
public class HaushaltsbuchUserDetailsService implements UserDetailsService
{

  private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchUserDetailsService.class);

  @Autowired
  private BenutzerRepository benutzerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    logger.info("Loading user with username {} ", username);

    UserDetails userDetails = null;

    if(Objects.nonNull(username)) {

      Benutzer benutzer = benutzerRepository.findByBenutzername(username);

      if(Objects.isNull(benutzer))
      {
        throw new UsernameNotFoundException("Benutzer "+username+" konnte in in der Datenbank gefunden werden.");
      }

      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("BENUTZER");

      userDetails = new User(benutzer.getBenutzername(),benutzer.getPasswort(),benutzer.isAktiviert(),benutzer.isAktiviert(),benutzer.isAktiviert(),benutzer.isAktiviert(),
          Arrays.asList(grantedAuthority));

      logger.info("Create user details {} ", userDetails.toString());
    }

    return userDetails;
  }
}
