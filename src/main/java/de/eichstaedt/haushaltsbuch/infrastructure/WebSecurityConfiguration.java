package de.eichstaedt.haushaltsbuch.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {


  @Autowired
  private HaushaltsbuchUserDetailsService userDetailsService;

  protected void configure(HttpSecurity http) throws Exception {

         http.csrf().disable().authorizeRequests().antMatchers("/css/**","/resources/**", "/registrierung/**", "/health","/anmeldung")
        .permitAll()
        .and()
        .formLogin().defaultSuccessUrl("/dashboard")
        .loginPage("/anmeldung")
        .permitAll();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/css/**","/resources/**");
  }

  @Bean
  public UserDetailsService userDetailsService() {

    return userDetailsService;
  }

}
