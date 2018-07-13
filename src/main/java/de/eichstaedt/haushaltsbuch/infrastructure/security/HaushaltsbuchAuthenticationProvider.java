package de.eichstaedt.haushaltsbuch.infrastructure.security;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HaushaltsbuchAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(HaushaltsbuchAuthenticationProvider.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        String password = (String) authentication.getCredentials();

        logger.info("Check username {} and passwort {} ", username,password);

        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            logger.error("User not found with that username {} ", username,e);
        }

        logger.info("Found UserDetails {} ", user);

        if (user == null || !user.getUsername().equals(username)) {
            throw new BadCredentialsException("Benutzername oder Passwort unbekannt.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Benutzername oder Passwort unbekannt.");
        }

        if(!user.isEnabled())
        {
            throw new CredentialsExpiredException("Benutzer ist nicht aktiviert");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
