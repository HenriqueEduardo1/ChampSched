package com.champsched.repository;

import com.champsched.model.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {
    
    @Query("SELECT DISTINCT c FROM Campeonato c JOIN c.times t JOIN t.integrantes i WHERE i.id = :userId")
    List<Campeonato> findByParticipanteId(@Param("userId") int userId);
    
}
