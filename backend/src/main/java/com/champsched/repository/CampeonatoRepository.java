package com.champsched.repository;

import com.champsched.model.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {
    
}
