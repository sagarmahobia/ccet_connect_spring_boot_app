/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.exceptions.InvalidCredentialsException;
import com.ccet.backend.api.v1.exceptions.UnknownDatabaseException;
import com.ccet.backend.api.v1.hibernate.entities.Otp;
import com.ccet.backend.api.v1.hibernate.entities.User;
import com.ccet.backend.api.v1.hibernate.repositories.UserRepository;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
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
        int userId = userRepository.saveUser(user);

        if (userId != -1) {
            String newOtp = String.format("%04d", new Random().nextInt(10000));

            Otp otp = new Otp();
            otp.setUserId(userId);
            otp.setOtp(newOtp);

            int i = userRepository.saveOtp(otp);
            mailService.sendOtp(user.getEmail(), newOtp);//sends mail

            Otp otp2 = new Otp();
            otp2.setId(i);
            return otp2;
        } else {
            throw new UnknownDatabaseException();
        }

    }

    public int verifyOtp(Otp otp) {
        int userId = userRepository.verifyOtp(otp);
        if (userId == -1) {
            userRepository.incrementAttempts(otp.getId());
            throw new InvalidCredentialsException();
        }
        userRepository.removeOtp(otp);
        userRepository.setVerified(userId);
        return userId;

    }


    public JwtUser signInUser(String email, String password) {

        return userRepository.verifySignInCreds(email, password);
    }


    public JwtUser getUserDetail(int id) {
        return userRepository.getJwtUser(id);
    }
}
