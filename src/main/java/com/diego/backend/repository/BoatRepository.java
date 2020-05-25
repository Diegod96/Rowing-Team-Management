package com.diego.backend.repository;

import com.diego.backend.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<Boat, Long> {

}
