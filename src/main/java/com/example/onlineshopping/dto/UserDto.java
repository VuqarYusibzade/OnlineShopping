package com.example.onlineshopping.dto;

import com.example.onlineshopping.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phone;
    private Role role;
    private String voen;
}
