package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.dto.AddressDto;
import com.example.onlineshopping.dto.UserDto;
import com.example.onlineshopping.models.Basket;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.BasketRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.service.MailService;
import com.example.onlineshopping.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUserTest() {
        UserDto userDto = new UserDto();
        userDto.setName("Test");
        userDto.setSurname("User");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password123");
        userDto.setPhone("1234567890");

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Test Street");
        addressDto.setCity("Test City");
        addressDto.setPostalCode("12345");
        addressDto.setCountry("Test Country");

        userService.registerUser(userDto, addressDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(basketRepository, Mockito.times(1)).save(Mockito.any(Basket.class));
        Mockito.verify(mailService, Mockito.times(1)).sendMail(Mockito.any(User.class));
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(Mockito.anyString());
        Mockito.verify(jwtService, Mockito.times(1)).saveTokenToDatabase(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void registerSellerTest() {
        UserDto userDto = new UserDto();
        userDto.setName("Test");
        userDto.setSurname("Seller");
        userDto.setEmail("seller@example.com");
        userDto.setPassword("password123");
        userDto.setPhone("1234567890");
        userDto.setVoen("1234567890");

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Test Street");
        addressDto.setCity("Test City");
        addressDto.setPostalCode("12345");
        addressDto.setCountry("Test Country");

        userService.registerSeller(userDto, addressDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(basketRepository, Mockito.times(1)).save(Mockito.any(Basket.class));
        Mockito.verify(mailService, Mockito.times(1)).sendMail(Mockito.any(User.class));
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(Mockito.anyString());
        Mockito.verify(jwtService, Mockito.times(1)).saveTokenToDatabase(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getUserByEmailTest() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getEmail());
    }

}

