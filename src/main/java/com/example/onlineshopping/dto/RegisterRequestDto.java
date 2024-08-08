package com.example.onlineshopping.dto;

import com.example.onlineshopping.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private UserDto userDto;
    private AddressDto addressDto;
    private User user;
}
