package de.eichstaedt.haushaltsbuch.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by konrad.eichstaedt@gmx.de on 05.05.18.
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

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
}
