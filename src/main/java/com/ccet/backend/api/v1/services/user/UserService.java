/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.services.user;

import com.ccet.backend.api.v1.repository.UserRepository;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SAGAR MAHOBIA
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String saveTempEmail(String email) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        userRepository.saveTempEmail(email, otp);
        return otp;
    }

}
