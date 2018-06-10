package de.eichstaedt.haushaltsbuch.domain.services;

import de.eichstaedt.haushaltsbuch.application.JahresberichtModel;
import de.eichstaedt.haushaltsbuch.application.KategorieBerichtModel;
import de.eichstaedt.haushaltsbuch.domain.controller.ZahlungsflussBoundaryController;
import de.eichstaedt.haushaltsbuch.domain.entities.Haushaltsbuch;
import de.eichstaedt.haushaltsbuch.domain.entities.Zahlungsfluss;
import de.eichstaedt.haushaltsbuch.domain.repository.HaushaltsbuchRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.KategorieRepository;
import de.eichstaedt.haushaltsbuch.domain.repository.ZahlungsflussRepository;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Kategorie;
import de.eichstaedt.haushaltsbuch.domain.valueobjects.Zahlungstyp;
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
public class Zahlungsservice implements ZahlungsflussBoundaryController {

    @Autowired
    private HaushaltsbuchRepository haushaltsbuchRepository;

    @Autowired
    private ZahlungsflussRepository zahlungsflussRepository;

    @Autowired
    private KategorieRepository kategorieRepository;

    private static final Logger logger = LoggerFactory.getLogger(Zahlungsservice.class);

    @Override
    public boolean buchen(Haushaltsbuch haushaltsbuch, Zahlungsfluss zahlung) {

        if(Objects.nonNull(haushaltsbuch) && Objects.nonNull(zahlung))
        {
            zahlung.setBuchid(haushaltsbuch.getId());

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
    public Page<Zahlungsfluss> findAllPageable(Pageable pageable, Long buchid) {

        return zahlungsflussRepository.findAllByBuchid(pageable, buchid);
    }

    @Override
    public JahresberichtModel createJahresbericht(Long buchid, int year) {

        logger.info("Creating Jahresbericht for Buch {} und Jahr {} ", buchid, year);

        List<Zahlungsfluss> januar = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,1,1,31);
        List<Zahlungsfluss> februar = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,2,1,28);
        List<Zahlungsfluss> maerz = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,3,1,31);
        List<Zahlungsfluss> april = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,4,1,30);
        List<Zahlungsfluss> mai = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,5,1,31);
        List<Zahlungsfluss> juni = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,6,1,30);
        List<Zahlungsfluss> juli = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,7,1,31);
        List<Zahlungsfluss> august = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,8,1,31);
        List<Zahlungsfluss> september = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,9,1,30);
        List<Zahlungsfluss> oktober = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,10,1,31);
        List<Zahlungsfluss> november = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,11,1,30);
        List<Zahlungsfluss> dezember = getZahlungsflussesForBuchAndYearAndMonth(buchid, year,12,1,31);

        januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum);


        double[] ausgaben = new double[]{januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            februar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            maerz.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            april.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            mai.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            juni.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            juli.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            august.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            september.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            oktober.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            november.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            dezember.stream().filter(z -> z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0)};

        double[] einnahmen = new double[]{januar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            februar.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            maerz.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            april.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            mai.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            juni.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            juli.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            august.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            september.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            oktober.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            november.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0),
            dezember.stream().filter(z -> z.getTyp().equals(Zahlungstyp.EINNAHME)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0)};

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

        List<Zahlungsfluss> zahlungen = zahlungsflussRepository.findByBuchidAndBuchungsTagBetween(buchid,LocalDate.of(year,1,1),LocalDate.of(year,12,31));

        List<Kategorie> kategories = new ArrayList<>();

        kategorieRepository.findAll().forEach(kategories::add);

        Object[][] berichtsWerte = new Object[kategories.size()][2];

        for(int i=0; i < kategories.size();i++)
        {
            Kategorie k = kategories.get(i);

            double betrag = zahlungen.stream().filter(z -> z.getKategorie().getName().equals(k.getName()) && z.getTyp().equals(Zahlungstyp.AUSGABE)).map(Zahlungsfluss::getBetrag).reduce(Double::sum).orElse(0.0);

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

    private List<Zahlungsfluss> getZahlungsflussesForBuchAndYearAndMonth(Long buchid, int year, int month, int startDay, int endDay) {
        return zahlungsflussRepository.findByBuchidAndBuchungsTagBetween(buchid,LocalDate
            .of(year,month,startDay),LocalDate.of(year,month,endDay));
    }
}
