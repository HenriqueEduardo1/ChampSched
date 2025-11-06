package com.champsched.repository;

import com.champsched.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {
    
    @Query("SELECT t FROM Time t JOIN t.integrantes i WHERE i.id = :userId")
    List<Time> findByIntegrantesId(@Param("userId") int userId);
    
}
