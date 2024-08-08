package com.example.onlineshopping.service;

import com.example.onlineshopping.dto.AddressDto;
import com.example.onlineshopping.dto.ChangePasswordRequest;
import com.example.onlineshopping.dto.UserDto;
import com.example.onlineshopping.enums.Role;
import com.example.onlineshopping.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;


public interface UserService extends UserDetailsService {
    void registerUser(UserDto userDto, AddressDto addressDto);

    Role getUserRole(String email);

    boolean isUserBlocked(String username);

    boolean isProductBlocked(String productId);

    boolean authenticateUser(String email, String password);

    void registerSeller(UserDto userDto, AddressDto addressDto);

    void registerAdmin(User user, AddressDto addressDto);

    void blockSeller(Long userId);

    List<User> getAllUsers();

    void changePassword(ChangePasswordRequest request, Principal connectUser);

    User getUserByEmail(String email);
}
