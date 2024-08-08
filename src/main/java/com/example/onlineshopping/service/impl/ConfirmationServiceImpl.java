package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.models.Confirmation;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.ConfirmationRepository;
import com.example.onlineshopping.repository.TokenRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.service.ConfirmationService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ConfirmationServiceImpl implements ConfirmationService {

    @Autowired
    private final ConfirmationRepository confirmationRepository;

    @Autowired
    private final EntityManager entityManager;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private UserRepository repository;
    @Autowired
    private final TokenRepository tokenRepository;

    public ConfirmationServiceImpl(ConfirmationRepository confirmationRepository, EntityManager entityManager, JwtService jwtService, TokenRepository tokenRepository) {
        this.confirmationRepository = confirmationRepository;
        this.entityManager = entityManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;


    }

    @Override
    public String generateConfirmationToken(User user) {
        Confirmation confirmation = new Confirmation();
        confirmation.setUser(user);
        confirmation.setConfirmationToken(UUID.randomUUID().toString());
        confirmationRepository.save(confirmation);
        return confirmation.getConfirmationToken();
    }

    @Override
    public boolean confirmUser(String token) {
        List<Confirmation> confirmations = entityManager.createQuery(
                        "SELECT c FROM Confirmation c WHERE c.confirmationToken = :token", Confirmation.class)
                .setParameter("token", token)
                .getResultList();

        if (!confirmations.isEmpty()) {
            Confirmation confirmation = confirmations.get(0);
            User user = confirmation.getUser();
            if (user != null) {
                user.setActive(true);
                confirmationRepository.delete(confirmation);
                return true;
            }
        }
        return false;
    }


}
