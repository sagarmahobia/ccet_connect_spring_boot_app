/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.exceptions.InvalidCredentialsException;
import com.ccet.backend.api.v1.hibernate.entities.Otp;
import com.ccet.backend.api.v1.hibernate.entities.User;
import com.ccet.backend.api.v1.hibernate.repositories.UserRepository;
import com.ccet.backend.api.v1.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author SAGAR MAHOBIA
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    public Otp signUpUser(User user) {
        userRepository.saveUser(user);

        String newOtp = String.format("%04d", new Random().nextInt(10000));

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtp(newOtp);

        int i = userRepository.saveOtp(otp);
        mailService.sendOtp(user.getEmail(), newOtp);//sends mail

        Otp otp2 = new Otp();
        otp2.setId(i);
        return otp2;
    }

    public User verifyOtp(Otp otp) {
        User user = userRepository.verifyOtp(otp);
        if (user == null) {
            userRepository.incrementAttempts(otp.getId());
            throw new InvalidCredentialsException();
        }
        userRepository.removeOtp(otp);
        userRepository.setVerifiedEmail(user);
        return user;

    }


    public User signInUser(String email, String password) {

        return userRepository.verifySignInCreds(email, password);
    }


}
