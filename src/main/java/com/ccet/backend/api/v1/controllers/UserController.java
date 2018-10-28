/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.jwtsecurity.security.JwtUtil;
import com.ccet.backend.api.v1.models.usermodels.*;
import com.ccet.backend.api.v1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SAGAR MAHOBIA
 */
@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(path = "/api/v1/public/user/request_otp", method = RequestMethod.POST)
    public OtpStatus register(@RequestBody OtpModel otpModel) {
        String email = otpModel.getEmail();
        OtpStatus otpStatus = new OtpStatus();

        if (email != null && email.length() > 0) {

            // todo verify email
            // todo check if email exists in users or in temp user
            try {
                userService.saveTempEmail(email);
            } catch (DuplicateKeyException e) {
                otpStatus.setStatus("Email already exists.");
                return otpStatus;
            }
            // todo send email
            otpStatus.setStatus("Check your mailbox for the OTP.");
            return otpStatus;
        } else {
            //todo add functionality
            //todo throw aspectJ error
            otpStatus.setStatus("Bad Request");
            return otpStatus;
        }
    }

    @RequestMapping(path = "/api/v1/public/user/verify_otp", method = RequestMethod.POST)
    public TempUser verifyOtp(@RequestBody OtpModel otpModel) {
        String email = otpModel.getEmail();
        String otp = otpModel.getOtp();

        TempUser tempUser = new TempUser();

        if (email != null && email.length() > 0 && otp.length() == 4) { //todo verify email
            int id = userService.verifyOtp(email, otp);
            tempUser.setId(id);
        }
        return tempUser;
    }

    @RequestMapping(path = "/api/v1/public/user/register_users", method = RequestMethod.POST)
    public String registerUser(@RequestBody RegistrableUser registrableUser) {

        //todo verify credentials
        int id = userService.verifyAndRegisterUser(registrableUser);

        if (id == -1) {
            return "invalid";
        }

        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(id);
        jwtUser.setFirstName(registrableUser.getFirstName());
        jwtUser.setLastName(registrableUser.getLastName());


        return jwtUtil.generate(jwtUser);
    }

    @RequestMapping(path = "/api/v1/public/user/sign_in", method = RequestMethod.POST)
    public String signInUser(@RequestBody SignInUser signInUser) {

        //todo verify email and password
        JwtUser jwtUser = userService.signInUser(signInUser.getEmail(), signInUser.getPassword());

        return jwtUtil.generate(jwtUser);
    }

}
