/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.exceptions.InvalidCredentialsException;
import com.ccet.backend.api.v1.exceptions.UnknownDatabaseException;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.commonmodels.SignUpModel;
import com.ccet.backend.api.v1.repository.UserRepository;
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


    public int verifyOtp(String email, String otp) {
        int id = userRepository.verifyOtp(email, otp);
        if (id == -1) {
            userRepository.incrementAttempts(email);
            throw new InvalidCredentialsException();
        }

        userRepository.setVerified(id);
        return id;

    }


    public JwtUser signInUser(String email, String password) {

        return userRepository.verifySignInCreds(email, password);
    }

    public void signUpUser(SignUpModel signUpModel) {
        int userId = userRepository.saveUser(signUpModel);

        if (userId != -1) {
            String otp = String.format("%04d", new Random().nextInt(10000));
            userRepository.saveOtp(userId, otp);
            mailService.sendOtp(signUpModel.getEmail(), otp);//sends mail
        } else {
            throw new UnknownDatabaseException();
        }

    }

    public JwtUser getUserDetail(int id) {
        return userRepository.getJwtUser(id);
    }
}
