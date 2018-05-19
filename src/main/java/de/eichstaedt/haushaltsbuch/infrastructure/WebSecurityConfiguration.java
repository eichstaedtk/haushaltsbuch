package de.eichstaedt.haushaltsbuch.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {

  private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Autowired
  private AuthenticationProvider authProvider;

  @Autowired
  private HaushaltsbuchUserDetailsService userDetailsService;

  protected void configure(HttpSecurity http) throws Exception {

         http.csrf().disable().authorizeRequests().antMatchers("/css/**","/images/**","/resources/**", "/registrierung/**", "/health","/anmeldung").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().defaultSuccessUrl("/dashboard")
        .loginPage("/anmeldung").permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    logger.info("Setup user configuration");

    auth.authenticationProvider(authProvider);

  }

  @Bean
  public UserDetailsService userDetailsService() {

    return userDetailsService;
  }
}
