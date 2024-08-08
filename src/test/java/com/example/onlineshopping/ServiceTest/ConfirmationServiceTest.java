package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.models.Confirmation;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.ConfirmationRepository;
import com.example.onlineshopping.service.impl.ConfirmationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class ConfirmationServiceTest {

    @Mock
    private ConfirmationRepository confirmationRepository;

    @InjectMocks
    private ConfirmationServiceImpl confirmationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateConfirmationTokenTest() {
        User user = new User();
        Confirmation confirmation = new Confirmation();
        confirmation.setUser(user);
        confirmation.setConfirmationToken(UUID.randomUUID().toString());

        Mockito.when(confirmationRepository.save(Mockito.any())).thenReturn(confirmation);

        String token = confirmationService.generateConfirmationToken(user);

        Assertions.assertNotNull(token);
        Mockito.verify(confirmationRepository, Mockito.times(1)).save(Mockito.any());
    }


}
