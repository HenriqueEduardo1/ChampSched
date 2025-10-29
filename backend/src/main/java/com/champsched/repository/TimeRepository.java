package com.champsched.repository;

import com.champsched.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {
    
}
