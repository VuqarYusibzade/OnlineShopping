package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.dto.AddressDto;
import com.example.onlineshopping.dto.ChangePasswordRequest;
import com.example.onlineshopping.dto.UserDto;
import com.example.onlineshopping.enums.Role;
import com.example.onlineshopping.models.Address;
import com.example.onlineshopping.models.Basket;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.BasketRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.service.MailService;
import com.example.onlineshopping.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final BasketRepository basketRepository;
    private final JwtService jwtService;


    @Override
    public void registerUser(UserDto userDto, AddressDto addressDto) {

        User user = User.builder().name(userDto.getName()).surname(userDto.getSurname()).password(userDto.getPassword())
                .email(userDto.getEmail()).phone(userDto.getPhone()).role(Role.USER).registrationDate(LocalDate.now())
                .address(new ArrayList<>()).build();
        userRepository.save(user);

        Address address = Address.builder()
                .street(addressDto.getStreet()).city(addressDto.getCity()).postalCode(addressDto.getPostalCode())
                .country(addressDto.getCountry()).user(user).build();

        user.getAddress().add(address);
        userRepository.save(user);

        Basket basket = Basket.builder()
                .user(user)
                .build();
        basketRepository.save(basket);
        mailService.sendMail(user);

        String token = jwtService.generateToken(user.getEmail());
        jwtService.saveTokenToDatabase(token, user.getEmail());

    }

    public boolean isUserBlocked(String username) {
        return false;
    }

    public boolean isProductBlocked(String productId) {
        return false;
    }

    @Override
    public void registerSeller(UserDto userDto, AddressDto addressDto) {

        User user = User.builder().name(userDto.getName()).surname(userDto.getSurname()).password(userDto.getPassword())
                .email(userDto.getEmail()).phone(userDto.getPhone()).voen(userDto.getVoen()).role(Role.SELLER).registrationDate(LocalDate.now())
                .address(new ArrayList<>()).build();
        userRepository.save(user);

        Address address = Address.builder()
                .street(addressDto.getStreet()).city(addressDto.getCity()).postalCode(addressDto.getPostalCode())
                .country(addressDto.getCountry()).user(user).build();

        user.getAddress().add(address);
        userRepository.save(user);
        mailService.sendMail(user);

        String token = jwtService.generateToken(user.getEmail());
        jwtService.saveTokenToDatabase(token, user.getEmail());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public void registerAdmin(User adminUser, AddressDto addressDto) {

        User user = User.builder().name(adminUser.getName()).surname(adminUser.getSurname()).password(adminUser.getPassword())
                .email(adminUser.getEmail()).phone(adminUser.getPhone()).role(Role.ADMIN).registrationDate(LocalDate.now())
                .address(new ArrayList<>()).build();
        userRepository.save(user);

        Address address = Address.builder()
                .street(addressDto.getStreet()).city(addressDto.getCity()).postalCode(addressDto.getPostalCode())
                .country(addressDto.getCountry()).user(user).build();

        user.getAddress().add(address);
        userRepository.save(user);
        mailService.sendMail(user);

        String token = jwtService.generateToken(user.getEmail());
        jwtService.saveTokenToDatabase(token, user.getEmail());
    }

    @Override
    public Role getUserRole(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user != null) {
            return user.get().getRole();
        } else {
            return Role.USER;
        }
    }

    @Override
    public void blockSeller(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setRole(Role.BLOCKED);
            userRepository.save(user);
        });
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        return user.orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);

        return userOptional.isPresent();
    }

}
