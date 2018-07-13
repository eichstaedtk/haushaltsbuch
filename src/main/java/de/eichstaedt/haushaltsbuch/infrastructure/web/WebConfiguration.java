package de.eichstaedt.haushaltsbuch.infrastructure.web;

import de.eichstaedt.haushaltsbuch.application.validation.CurrencyConverter;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

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

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new CurrencyConverter());
  }

  @Bean
  public CookieLocaleResolver localeResolver() {
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setDefaultLocale(Locale.GERMAN);
    return localeResolver;
  }
}
