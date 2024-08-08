package com.example.onlineshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/home")
    public String main() {
        return "main";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/seller-register")
    public String SellerRegister() {
        return "seller-registration";
    }

    @GetMapping("/confirm-user")
    public String ConfirmUser() {
        return "confirmmail";
    }

    @GetMapping("/log-in")
    public String login() {
        return "login";
    }

    @GetMapping("/log-in-seller")
    public String loginseller() {
        return "loginseller";
    }

    @GetMapping("/seller-home")
    public String sellerhome() {
        return "sellerhome";
    }

    @GetMapping("/add-product")
    public String addproduct() {
        return "addproduct";
    }

    @GetMapping("/update-product")
    public String updateproduct() {
        return "updateproduct";
    }
}


