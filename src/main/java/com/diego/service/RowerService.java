package com.diego.service;

import com.diego.backend.entity.Boat;
import com.diego.backend.entity.Rower;
import com.diego.backend.repository.BoatRepository;
import com.diego.backend.repository.RowerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RowerService {
    private static final Logger LOGGER = Logger.getLogger(RowerService.class.getName());
    private RowerRepository rowerRepository;
    private BoatRepository boatRepository;

    public RowerService(RowerRepository rowerRepository,
                        BoatRepository boatRepository) {
        this.rowerRepository = rowerRepository;
        this.boatRepository = boatRepository;
    }

    public List<Rower> findAll() {
        return rowerRepository.findAll();
    }

    public List<Rower> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return rowerRepository.findAll();
        } else {
            return rowerRepository.search(filterText);
        }
    }

    public long count() {
        return rowerRepository.count();
    }

    public void delete(Rower rower) {
        rowerRepository.delete(rower);
    }

    public void save(Rower rower) {
        if (rower == null) {
            LOGGER.log(Level.SEVERE,
                    "Rower is null. Are you sure you have connected your form to the application?");
            return;
        }
        rowerRepository.save(rower);
    }

    @PostConstruct
    public void populateTestData() {
        if (boatRepository.count() == 0) {
            boatRepository.saveAll(Stream.of("Varsity 8", "Second Varsity 8", "Third Varsity 8", "Junior Varsity 8", "Lightweight 8", "Freshmen/Novice 8", "Varsity 4+", "Junior Varsity 4+", "Freshmen/Novice 4+",  "Lightweight 4+", "Lightweight 4x-", "Varsity 4x-", "Varsity 2-").map(Boat::new).collect(Collectors.toList()));
        }
    }
}
//
//        if (rowerRepository.count() == 0) {
//            Random r = new Random(0);
//            List<Boat> boats = boatRepository.findAll();
//            rowerRepository.saveAll(
//                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
//                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
//                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
//                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
//                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
//                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
//                            "Jaydan Jackson", "Bernard Nilsen")
//                            .map(name -> {
//                                String[] split = name.split(" ");
//                                Rower rower = new Rower();
//                                rower.setFirstName(split[0]);
//                                rower.setLastName(split[1]);
//                                rower.setBoat(boats.get(r.nextInt(boats.size())));
//                                rower.setYear(Rower.Year.values()[r.nextInt(Rower.Year.values().length)]);
//                                String email = (rower.getFirstName() + "." + rower.getLastName() + "@" + rower.getBoat().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
//                                rower.setEmail(email);
//                                return rower;
//                            }).collect(Collectors.toList()));
//        }
//    }
