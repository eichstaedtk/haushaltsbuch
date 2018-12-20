package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.application.model.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.application.model.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.EinmaligeZahlung;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.KategorieRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.EinmalZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ZahlungsService implements ZahlungsflussBoundaryController {

    @Autowired
    private HaushaltsbuchRepository haushaltsbuchRepository;

    @Autowired
    private EinmalZahlungsflussRepository einmalZahlungsflussRepository;

    @Autowired
    private KategorieRepository kategorieRepository;

    private static final Logger logger = LoggerFactory.getLogger(ZahlungsService.class);

    @Override
    public boolean buchen(Haushaltsbuch haushaltsbuch, EinmaligeZahlung zahlung) {

        if(Objects.nonNull(haushaltsbuch) && Objects.nonNull(zahlung))
        {
            zahlung.setBuchid(haushaltsbuch.getId());

            EinmaligeZahlung saved = einmalZahlungsflussRepository.save(zahlung);

            if(Objects.nonNull(saved))
            {
                if(saved.getTyp().equals(Zahlungstyp.AUSGABE))
                {
                    haushaltsbuch.getAusgaben().add(saved);
                }else {
                    haushaltsbuch.getEinnahmen().add(saved);
                }

                haushaltsbuch.setAenderungsdatum(LocalDate.now());
                haushaltsbuch = haushaltsbuchRepository.save(haushaltsbuch);

                logger.info("Haushaltsbuch saved {}",haushaltsbuch);

                return true;
            }

        }
        return false;
    }

    @Override
    public Optional<EinmaligeZahlung> laden(String zahlungsid) {

        Optional<EinmaligeZahlung> zahlungsfluss = null;

        if(Objects.nonNull(zahlungsid))
        {
            zahlungsfluss = einmalZahlungsflussRepository.findById(Long.parseLong(zahlungsid));
        }

        return zahlungsfluss;
    }

    @Override
    public boolean loeschen(String haushaltsbuchid, String zahlungid) {

        if(Objects.nonNull(haushaltsbuchid) && Objects.nonNull(zahlungid))
        {

            Optional<Haushaltsbuch> buch = haushaltsbuchRepository.findById(Long.parseLong(haushaltsbuchid));

            if(buch.isPresent()) {

                Optional<EinmaligeZahlung> loeschen = einmalZahlungsflussRepository
                    .findById(Long.parseLong(zahlungid));

                if (loeschen.isPresent()) {

                    if(loeschen.get().getTyp().equals(Zahlungstyp.AUSGABE)) {
                        buch.get().getAusgaben().remove(loeschen.get());
                    }else {
                        buch.get().getEinnahmen().remove(loeschen.get());
                    }

                    buch.get().setAenderungsdatum(LocalDate.now());
                    haushaltsbuchRepository.save(buch.get());

                    einmalZahlungsflussRepository.delete(loeschen.get());

                    return true;
                }

            }

        }


        return false;
    }

    @Override
    public Page<EinmaligeZahlung> findAllPageable(Pageable pageable, Long buchid) {

        return einmalZahlungsflussRepository.findAllByBuchid(pageable, buchid);
    }

    @Override
    public JahresberichtModel createJahresbericht(Long buchid, int year) {

        logger.info("Creating Jahresbericht for Buch {} und Jahr {} ", buchid, year);

        List<EinmaligeZahlung> januar = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,1,1,31);
        List<EinmaligeZahlung> februar = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,2,1,28);
        List<EinmaligeZahlung> maerz = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,3,1,31);
        List<EinmaligeZahlung> april = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,4,1,30);
        List<EinmaligeZahlung> mai = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,5,1,31);
        List<EinmaligeZahlung> juni = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,6,1,30);
        List<EinmaligeZahlung> juli = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,7,1,31);
        List<EinmaligeZahlung> august = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,8,1,31);
        List<EinmaligeZahlung> september = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,9,1,30);
        List<EinmaligeZahlung> oktober = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,10,1,31);
        List<EinmaligeZahlung> november = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,11,1,30);
        List<EinmaligeZahlung> dezember = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,12,1,31);

        januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add);


        BigDecimal[] ausgaben = new BigDecimal[]{januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            februar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            maerz.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            april.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            mai.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            juni.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            juli.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            august.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            september.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            oktober.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            november.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            dezember.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0))};

        BigDecimal[] einnahmen = new BigDecimal[]{januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            februar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            maerz.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            april.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            mai.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            juni.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            juli.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            august.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            september.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            oktober.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            november.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
            dezember.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0))};

        Optional<Haushaltsbuch> haushaltsbuch = haushaltsbuchRepository.findById(buchid);

        if(haushaltsbuch.isPresent())
        {
            return new JahresberichtModel(ausgaben,einnahmen,"Jahresbericht "+year+" "+haushaltsbuch.get().getName());
        }else {
            return new JahresberichtModel(ausgaben,einnahmen,"Jahresbericht "+year);
        }

    }

    @Override
    public KategorieBerichtModel createJahresKategoriebericht(Long buchid, int year) {

        List<EinmaligeZahlung> zahlungen = einmalZahlungsflussRepository
            .findByBuchidAndBuchungsTagBetween(buchid,LocalDate.of(year,1,1),LocalDate.of(year,12,31));

        List<Kategorie> kategories = new ArrayList<>();

        kategorieRepository.findAll().forEach(kategories::add);

        Object[][] berichtsWerte = new Object[kategories.size()][2];

        for(int i=0; i < kategories.size();i++)
        {
            Kategorie k = kategories.get(i);

            BigDecimal betrag = zahlungen.stream().filter(z -> z.getKategorie().getName().equals(k.getName()) && z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(BigDecimal::add).orElse(new BigDecimal(0));

            berichtsWerte[i] = new Object[]{k.getName(),betrag};

            logger.info("Add new Kategorien Value {} {}", k.getName(),betrag);
        }

        Optional<Haushaltsbuch> haushaltsbuch = haushaltsbuchRepository.findById(buchid);

        KategorieBerichtModel kategorieBerichtModel = null;


        if(haushaltsbuch.isPresent())
        {
            kategorieBerichtModel =  new KategorieBerichtModel(berichtsWerte,"Kategorien Ausgaben Jahresbericht "+year+" "+haushaltsbuch.get().getName());
        }else {
            kategorieBerichtModel =  new KategorieBerichtModel(berichtsWerte,"Kategorien Ausgaben Jahresbericht "+year);
        }

        return kategorieBerichtModel;
    }

    private List<EinmaligeZahlung> getZahlungsflussesForBuchAndYearAndMonth(Long buchid, int year, int month, int startDay, int endDay) {
        return einmalZahlungsflussRepository.findByBuchidAndBuchungsTagBetween(buchid,LocalDate
            .of(year,month,startDay),LocalDate.of(year,month,endDay));
    }
}
