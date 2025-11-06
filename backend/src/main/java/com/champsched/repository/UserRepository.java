package com.champsched.repository;

import com.champsched.dto.UserResponseDTO;
import com.champsched.model.User;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.id, u.nome, u.contato FROM users u WHERE u.id = :id", nativeQuery = true)
    UserResponseDTO findById(@Param("id") Integer id);

    Optional<User> findUserById(Integer id);
}
