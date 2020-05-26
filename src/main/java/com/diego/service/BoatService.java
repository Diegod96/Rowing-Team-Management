package com.diego.service;

import com.diego.backend.entity.Boat;
import com.diego.backend.repository.BoatRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoatService {

    private BoatRepository boatRepository;

    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(boat ->
                stats.put(boat.getName(), boat.getRowers().size()));

        return stats;
    }
}