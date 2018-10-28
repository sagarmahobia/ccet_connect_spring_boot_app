/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.usermodels.RegistrableUser;
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

    public void saveTempEmail(String email) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        userRepository.saveTempEmail(email, otp);
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

    public void saveAppUser(RegistrableUser registrableUser) {
        userRepository.registerNewUser(registrableUser);

    }

    public int verifyAndRegisterUser(RegistrableUser registrableUser) {
        if (userRepository.verifyRegistrableUser(registrableUser.getTemp_id(), registrableUser.getEmail())) {
            return userRepository.registerNewUser(registrableUser);
        }
        return -1;
    }

    public JwtUser signInUser(String email, String password) {

        return userRepository.verifySignInCreds(email, password);
    }
}
