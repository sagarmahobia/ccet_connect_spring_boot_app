/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SAGAR MAHOBIA
 */
@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveTempEmail(String email, String otp) {
        jdbcTemplate.update(SQL.INSERT_EMAIL_OTP, email, otp);
    }

    private interface SQL {

        String INSERT_EMAIL_OTP = "INSERT INTO `temp_user`(`email`, `otp`) VALUES ( ? , ? )";
    }
}
