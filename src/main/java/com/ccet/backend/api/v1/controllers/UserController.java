/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.jwtsecurity.security.JwtUtil;
import com.ccet.backend.api.v1.models.commonmodels.AuthStatus;
import com.ccet.backend.api.v1.models.commonmodels.SignUpModel;
import com.ccet.backend.api.v1.models.commonmodels.Status;
import com.ccet.backend.api.v1.models.usermodels.OtpModel;
import com.ccet.backend.api.v1.models.usermodels.SignInUser;
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
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @RequestMapping(path = "/api/v1/public/user/sign_up", method = RequestMethod.POST)
    public Status signUp(@RequestBody SignUpModel signUpModel) {
        String email = signUpModel.getEmail();
        String firstName = signUpModel.getFirstName();
        String lastName = signUpModel.getLastName();
        String admissionYear = signUpModel.getAdmissionYear();
        int admissionSemester = signUpModel.getAdmissionSemester();
        String passWord = signUpModel.getPassWord();

        //todo verify credentials

        boolean b = userService.signUpUser(signUpModel);

        Status status = new Status();
        if (b) {
            status.setStatus("success");
            return status;
        } else {
            status.setStatus("failure");
            return status;
        }
    }

    @RequestMapping(path = "/api/v1/public/user/verify_otp", method = RequestMethod.POST)
    public AuthStatus verifyOtp(@RequestBody OtpModel otpModel) {
        String email = otpModel.getEmail();
        String otp = otpModel.getOtp();

        String token = null;
        //todo verify email
        int id = userService.verifyOtp(email, otp);
        if (id != -1) {
            token = jwtUtil.generate(userService.getUserDetail(id));
        }
        AuthStatus authStatus = new AuthStatus();

        authStatus.setToken(token);
        authStatus.setAuthenticated(token != null);

        return authStatus;
    }


    @RequestMapping(path = "/api/v1/public/user/sign_in", method = RequestMethod.POST)
    public AuthStatus signInUser(@RequestBody SignInUser signInUser) {

        //todo verify email and password
        JwtUser jwtUser = userService.signInUser(signInUser.getEmail(), signInUser.getPassword());

        boolean authenticated = jwtUser != null;

        AuthStatus authStatus = new AuthStatus();
        authStatus.setAuthenticated(authenticated);
        if (authenticated) {
            authStatus.setToken(jwtUtil.generate(jwtUser));
        }
        return authStatus;
    }

}
