package de.eichstaedt.haushaltsbuch.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {

  private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Autowired
  private AuthenticationProvider authProvider;

  protected void configure(HttpSecurity http) throws Exception {

         http.csrf().disable().authorizeRequests().antMatchers("/webjars/**","/css/**","/images/**","/resources/**", "/registrierung/**", "/health","/anmeldung").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().defaultSuccessUrl("/dashboard")
        .loginPage("/anmeldung").permitAll()
             .and().logout().logoutSuccessUrl("/anmeldung").invalidateHttpSession(true);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    logger.info("Setup user configuration");

    auth.authenticationProvider(authProvider);

  }
}
