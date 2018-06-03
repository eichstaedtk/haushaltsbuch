package de.eichstaedt.haushaltsbuch;

import de.eichstaedt.haushaltsbuch.application.Registrierung;
import de.eichstaedt.haushaltsbuch.domain.controller.BenutzerBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.controller.KategorieBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Benutzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Autowired
	private BenutzerBoundaryController benutzerBoundaryController;

	private static final Logger logger = LoggerFactory.getLogger(HaushaltsbuchApplication.class);

	public static void main(String[] args) {
		logger.info("########### HAUSHALTSBUCH STARTING APPLICATION #############");
		SpringApplication.run(HaushaltsbuchApplication.class, args);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void createKategories() {

		kategorieBoundaryController.createDefaultKategories();

		if(benutzerBoundaryController.isBenutzernameFree("konrad")) {
			Registrierung defaultBenutzer = new Registrierung.RegistrierungBuilder(
					"konrad.eichstaedt@gmx.de", "konrad", "Start123", "Start123")
					.withName("Konrad", "Eichstädt")
					.withAdresse("Rathenow", "Göttliner Dorfstraße 12a", "14712", "Deutschland")
					.build();

			Benutzer konrad = benutzerBoundaryController
					.erstelleAnwendungsBenutzerVonRegistrierung(defaultBenutzer);

			benutzerBoundaryController.aktiviereBenutzerMitCode(konrad.getAktivierungsCode());

			logger.info("Created default benutzer konrad {} ", konrad);
		}


	}
}
