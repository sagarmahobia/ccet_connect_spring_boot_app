/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.commonmodels.SignUpModel;
import com.ccet.backend.api.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author SAGAR MAHOBIA
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public int verifyOtp(String email, String otp) {
        int id = userRepository.verifyOtp(email, otp);
        if (id == -1) {
            userRepository.incrementAttempts(email);
        } else {
            userRepository.setVerified(id);
        }
        return id;
    }


    public JwtUser signInUser(String email, String password) {

        return userRepository.verifySignInCreds(email, password);
    }

    public boolean signUpUser(SignUpModel signUpModel) {
        int userId = userRepository.saveUser(signUpModel);

        if (userId != -1) {
            String otp = String.format("%04d", new Random().nextInt(10000));
            userRepository.saveOtp(userId, otp);
            //todo send email
            return true;
        }
        return false;
    }

    public JwtUser getUserDetail(int id) {
        return userRepository.getJwtUser(id);
    }
}
