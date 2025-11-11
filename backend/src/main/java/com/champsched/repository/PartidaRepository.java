package com.champsched.repository;

import com.champsched.model.PartidaFutebol;
import com.champsched.model.StatusPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<PartidaFutebol, Integer> {
    
    List<PartidaFutebol> findByCampeonatoIdOrderByFaseAsc(Integer campeonatoId);
    
    List<PartidaFutebol> findByCampeonatoIdAndFase(Integer campeonatoId, Integer fase);
    
    List<PartidaFutebol> findByCampeonatoIdAndStatus(Integer campeonatoId, StatusPartida status);
    
    long countByCampeonatoId(Integer campeonatoId);
    
    @Query("SELECT p FROM PartidaFutebol p WHERE p.campeonato.id = :campeonatoId " +
           "AND (p.timeA.id = :timeId OR p.timeB.id = :timeId)")
    List<PartidaFutebol> findByCampeonatoIdAndTimeId(
        @Param("campeonatoId") Integer campeonatoId, 
        @Param("timeId") Integer timeId
    );
}
