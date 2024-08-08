package com.example.onlineshopping.repository;

import com.example.onlineshopping.models.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Integer> {
    Confirmation findByConfirmationToken(String token);


}
