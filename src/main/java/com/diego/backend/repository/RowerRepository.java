package com.diego.backend.repository;

import com.diego.backend.entity.Rower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RowerRepository extends JpaRepository<Rower, Long> {
    @Query("select r from Rower r " +
            "where lower(r.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.lastName) like lower(concat('%', :searchTerm, '%'))") //
    List<Rower> search(@Param("searchTerm") String searchTerm); //

}
