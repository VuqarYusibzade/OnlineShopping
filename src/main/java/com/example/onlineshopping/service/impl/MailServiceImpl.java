package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.models.Confirmation;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.ConfirmationRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.service.ConfirmationService;
import com.example.onlineshopping.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    private final ConfirmationRepository confirmationRepository;
    private final UserRepository userRepository;
    private final ConfirmationService confirmationService;


    @Override
    public boolean verifyTokenAndActivateUser(String token) {
        Confirmation confirmation = confirmationRepository.findByConfirmationToken(token);
        if (confirmation != null) {
            User user = confirmation.getUser();
            user.setActive(true);
            userRepository.save(user);
            confirmationRepository.delete(confirmation);
            return true;
        }
        return false;
    }


    @Override
    public String sendMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vugaroy@code.edu.az");
        message.setTo(user.getEmail());
        message.setSubject("Confirmation Email");
        String confirmationToken = confirmationService.generateConfirmationToken(user);

        String emailText = "Hello. Thank you for registering. To activate your account, please verify your email using the code\n" + confirmationToken;
        message.setText(emailText);

        mailSender.send(message);
        return "Success";
    }

}
