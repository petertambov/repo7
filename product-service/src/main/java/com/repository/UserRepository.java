package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*@Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);*/
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE :prefix%")
    List<User> findByUsernameStartingWith(@Param("prefix") String prefix);
}