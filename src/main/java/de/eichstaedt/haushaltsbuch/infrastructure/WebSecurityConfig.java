package de.eichstaedt.haushaltsbuch.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */

@EnableWebMvc
public class WebSecurityConfig implements WebMvcConfigurer {

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public UserDetailsService userDetailsService() throws Exception {


    return userDetailsService;
  }

}
