/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.models.usermodels.OtpModel;
import com.ccet.backend.api.v1.models.usermodels.OtpStatus;
import com.ccet.backend.api.v1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SAGAR MAHOBIA
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/api/v1/user/request_otp", method = RequestMethod.POST)
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
}
