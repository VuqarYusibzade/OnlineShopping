package com.example.onlineshopping.config;

import com.example.onlineshopping.security.JwtAuthenticationFilter;
import com.example.onlineshopping.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                        x.requestMatchers(WHITE_LIST_URLS).permitAll()
                )
                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/users/**").hasRole("USER")
                                .requestMatchers("/api/users/allproduct").hasAnyRole("USER", "ADMIN", "SELLER")
                                .requestMatchers("/api/users/login").hasAnyRole("USER", "ADMIN", "SELLER")
                                .requestMatchers("/api/admin/allproducts").hasAnyRole("USER", "ADMIN", "SELLER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/seller-home").hasRole("SELLER")
                                .requestMatchers("/add-product").hasRole("SELLER")
                                .requestMatchers("/api/seller/**").hasRole("SELLER")
                                .requestMatchers("/api/confirm").hasAnyRole("ADMIN", "SELLER", "USER")
                                .requestMatchers("/api/users/change-password").hasAnyRole("ADMIN", "USER", "SELLER")

                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private static final String[] WHITE_LIST_URLS = {
            "/home",
            "/add-product",
            "/api/users/role",
            "/api/role/{email}",
            "/log-in-seller",
            "/seller-home",
            "/register",
            "/users/role",
            "/seller-register",
            "/confirm-user",
            "/log-in",
            "/update-product",
            "/api/users/register",
            "/api/users/login",
            "/api/users/place/{userId}",
            "/api/users/{basketItemId}",
            "/api/users/{basketItemId}/{userId}",
            "/api/users/{userId}/basket/add",
            "/api/users/{userId}/basket/add?productId={productId}",
            "/api/users/{userId}/basket/{productId}/updateQuantity/{newQuantity}",
            "/api/admin/register",
            "/api/users/place/{userId}",
            "/api/users/{userId}/basket/add?productId=",
            "/api/seller/register",
            "/api/seller/products/{productId}",
            "/api/confirm-token",
            "/api/confirm",
            "/api/seller/products",
            "/api/seller/{productId}/categories/{categoryId}",
            "/api/seller/deliver",
            "/api//generate-token",
            "/api/admin/addcatagories",
            "/api/admin/allorders",
            "/api/admin/pendingorders",
            "/api/admin/{userId}/blockseller",
            "/api/admin/{productId}/blockproduct",
            "/api/users/allproduct",
            "/api/users/change-password"


    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

}
