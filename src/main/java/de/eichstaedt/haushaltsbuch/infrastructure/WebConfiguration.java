package de.eichstaedt.haushaltsbuch.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/anmeldung").setViewName("anmeldung");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(
        "/webjars/**",
        "/images/**",
        "/css/**",
        "/js/**")
        .addResourceLocations(
            "classpath:/META-INF/resources/webjars/",
            "classpath:/static/images/",
            "classpath:/static/css/",
            "classpath:/static/js/");
  }


  @Bean
  public UserDetailsService userDetailsService() {

    return userDetailsService;
  }
}
