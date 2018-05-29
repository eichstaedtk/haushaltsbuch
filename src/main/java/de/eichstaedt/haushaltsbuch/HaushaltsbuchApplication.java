package de.eichstaedt.haushaltsbuch;

import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HaushaltsbuchApplication {

	@Autowired
	private KategorieBoundaryController kategorieBoundaryController;

	public static void main(String[] args) {

		SpringApplication.run(HaushaltsbuchApplication.class, args);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void createKategories() {

		kategorieBoundaryController.createDefaultKategories();

	}
}
