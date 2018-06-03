package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.ZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Zahlungsservice implements ZahlungsflussBoundaryController {

    @Autowired
    private HaushaltsbuchRepository haushaltsbuchRepository;

    @Autowired
    private ZahlungsflussRepository zahlungsflussRepository;

    private static final Logger logger = LoggerFactory.getLogger(Zahlungsservice.class);

    @Override
    public boolean buchen(Haushaltsbuch haushaltsbuch, Zahlungsfluss zahlung) {

        if(Objects.nonNull(haushaltsbuch) && Objects.nonNull(zahlung))
        {
            Zahlungsfluss saved = zahlungsflussRepository.save(zahlung);

            if(Objects.nonNull(saved))
            {
                if(saved.getTyp().equals(Zahlungstyp.AUSGABE))
                {
                    haushaltsbuch.getAusgaben().add(saved);
                }else {
                    haushaltsbuch.getEinnahmen().add(saved);
                }

                haushaltsbuch = haushaltsbuchRepository.save(haushaltsbuch);

                logger.info("Haushaltsbuch saved {}",haushaltsbuch);

                return true;
            }

        }
        return false;
    }

    @Override
    public Optional<Zahlungsfluss> laden(String zahlungsid) {

        Optional<Zahlungsfluss> zahlungsfluss = null;

        if(Objects.nonNull(zahlungsid))
        {
            zahlungsfluss = zahlungsflussRepository.findById(Long.parseLong(zahlungsid));
        }

        return zahlungsfluss;
    }

    @Override
    public boolean loeschen(String haushaltsbuchid, String zahlungid) {

        if(Objects.nonNull(haushaltsbuchid) && Objects.nonNull(zahlungid))
        {

            Optional<Haushaltsbuch> buch = haushaltsbuchRepository.findById(Long.parseLong(haushaltsbuchid));

            if(buch.isPresent()) {

                Optional<Zahlungsfluss> loeschen = zahlungsflussRepository
                    .findById(Long.parseLong(zahlungid));

                if (loeschen.isPresent()) {

                    if(loeschen.get().getTyp().equals(Zahlungstyp.AUSGABE)) {
                        buch.get().getAusgaben().remove(loeschen.get());
                    }else {
                        buch.get().getEinnahmen().remove(loeschen.get());
                    }

                    haushaltsbuchRepository.save(buch.get());

                    zahlungsflussRepository.delete(loeschen.get());

                    return true;
                }

            }

        }


        return false;
    }

    @Override
    public Page<Zahlungsfluss> findAllPageable(Pageable pageable) {

        return zahlungsflussRepository.findAll(pageable);
    }
}
