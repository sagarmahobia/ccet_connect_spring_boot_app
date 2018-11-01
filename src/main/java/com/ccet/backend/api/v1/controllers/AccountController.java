/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.exceptions.InternalServerException;
import com.ccet.backend.api.v1.exceptions.InvalidInputException;
import com.ccet.backend.api.v1.exceptions.UserNotFoundException;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.jwtsecurity.security.JwtUtil;
import com.ccet.backend.api.v1.models.commonmodels.*;
import com.ccet.backend.api.v1.services.ValidatorService;
import com.ccet.backend.api.v1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SAGAR MAHOBIA
 */
@RestController
public class AccountController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ValidatorService validatorService;

    @Autowired
    public AccountController(UserService userService, JwtUtil jwtUtil, ValidatorService validatorService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.validatorService = validatorService;
    }


    @RequestMapping(path = "/api/v1/public/user/sign_up", method = RequestMethod.POST)
    public Status signUp(@RequestBody SignUpModel signUpModel) {
        String email = signUpModel.getEmail();
        String firstName = signUpModel.getFirstName();
        String lastName = signUpModel.getLastName();
        String admissionYear = signUpModel.getAdmissionYear();
        int admissionSem = signUpModel.getAdmissionSemester();
        String passWord = signUpModel.getPassWord();


        if (firstName.isEmpty()
                || lastName.isEmpty()
                || !validatorService.isValidEmail(email)
                || !validatorService.isValidPassword(passWord)
                || !(admissionYear.length() == 4)
                || !(admissionSem >= 1 && admissionSem <= 8)
        ) {
            throw new InvalidInputException();
        }

        userService.signUpUser(signUpModel);

        Status status = new Status();
        status.setStatus("success");
        return status;
    }

    @RequestMapping(path = "/api/v1/public/user/verify_otp", method = RequestMethod.POST)
    public AuthStatus verifyOtp(@RequestBody OtpModel otpModel) {
        String email = otpModel.getEmail();
        String otp = otpModel.getOtp();


        try {
            Integer.parseInt(otp);
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }

        if (!validatorService.isValidEmail(email) || !(otp.length() == 4)) {
            throw new InvalidInputException();
        }

        int id = userService.verifyOtp(email, otp);

        String token = jwtUtil.generate(userService.getUserDetail(id));
        if (token == null) {
            throw new InternalServerException("jwtUtil.generate(token) failed");
        }

        AuthStatus authStatus = new AuthStatus();
        authStatus.setToken(token);
        return authStatus;
    }


    @RequestMapping(path = "/api/v1/public/user/sign_in", method = RequestMethod.POST)
    public AuthStatus signInUser(@RequestBody SignInUser signInUser) {

        String email = signInUser.getEmail();
        String password = signInUser.getPassword();

        if (!validatorService.isValidEmail(email) || !validatorService.isValidPassword(password)) {
            throw new InvalidInputException();
        }

        JwtUser jwtUser = userService.signInUser(email, password);
        if (jwtUser == null) {
            throw new UserNotFoundException();
        }

        AuthStatus authStatus = new AuthStatus();
        authStatus.setToken(jwtUtil.generate(jwtUser));
        return authStatus;
    }

}
