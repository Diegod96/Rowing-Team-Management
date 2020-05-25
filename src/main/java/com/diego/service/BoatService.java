package com.diego.service;

import com.diego.backend.entity.Boat;
import com.diego.backend.repository.BoatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {

    private BoatRepository boatRepository;

    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

}