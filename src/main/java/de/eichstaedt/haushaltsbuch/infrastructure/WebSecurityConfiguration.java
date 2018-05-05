package de.eichstaedt.haushaltsbuch.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {

  protected void configure(HttpSecurity http) throws Exception {

         http.authorizeRequests().antMatchers("/resources/**", "/registrierung/**", "/health","/anmeldung")
        .permitAll()
        .and()
        .formLogin()
        .loginPage("/anmeldung")
        .permitAll();
  }

}
