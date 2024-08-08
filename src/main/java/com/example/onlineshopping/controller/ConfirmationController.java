package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.AuthRequest;
import com.example.onlineshopping.enums.Role;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.service.MailService;
import com.example.onlineshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class ConfirmationController {
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/confirm-token")
    public ResponseEntity<String> confirmUser(@RequestParam String token) {
        boolean result = mailService.verifyTokenAndActivateUser(token);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(result ? "User successfully verified and activated." : "Invalid token.");
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @GetMapping("/role/{email}")
    public ResponseEntity<Role> getUserRole(@PathVariable String email) {
        Role role = userService.getUserRole(email);
        if (role != null) {
            return switch (role) {
                case USER -> ResponseEntity.ok(role);
                case SELLER -> ResponseEntity.ok(role);
                case ADMIN -> ResponseEntity.ok(role);
                default -> ResponseEntity.badRequest().build();
            };
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}