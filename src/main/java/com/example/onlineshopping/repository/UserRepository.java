package com.example.onlineshopping.repository;

import com.example.onlineshopping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByPassword(String password);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    Optional<User> findByEmailIgnoreCase(String email);
}
