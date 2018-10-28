/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.repository;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.usermodels.RegistrableUser;
import com.ccet.backend.api.v1.models.usermodels.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author SAGAR MAHOBIA
 */
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveTempEmail(String email, String otp) {
        jdbcTemplate.update(SQL.INSERT_EMAIL_OTP, email, otp);
    }

    public int verifyOtp(String email, String otp) {

        SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(SQL.VERIFY_OTP, email, otp);
        if (queryForRowSet.first()) {
            return queryForRowSet.getInt("id");
        } else {
            return -1;
        }
    }

    public void incrementAttempts(String email) {
        jdbcTemplate.update(SQL.INCREMENT_ATTEMPTS, email);
    }

    public void setVerified(int id) {
        jdbcTemplate.update(SQL.SET_VERIFIED, id);
    }

    public int registerNewUser(RegistrableUser registrableUser) {

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection c) -> {
            final PreparedStatement ps = c.prepareStatement(SQL.SAVE_USER,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, Roles.getRole(registrableUser.getRole()).getRoleId());
            ps.setString(2, registrableUser.getEmail());
            ps.setString(3, registrableUser.getPassword());//todo encrypt password
            ps.setString(4, registrableUser.getFirstName());
            ps.setString(5, registrableUser.getLastName());

            return ps;
        }, holder);
        Number key = holder.getKey();

        if (key == null) {
            return -1;
        }

        int newId = key.intValue();
        return newId;
    }

    public boolean verifyRegistrableUser(int temp_id, String email) {

        SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(SQL.VERIFY_NEWUSER, temp_id, email);
        return queryForRowSet.first();
    }

    public JwtUser verifySignInCreds(String email, String password) {
        SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(SQL.VERIFY_SIGN_IN, email, password);
        if (queryForRowSet.first()) {

            JwtUser jwtUser = new JwtUser();

            int id = queryForRowSet.getInt("id");
            String fistName = queryForRowSet.getString("first_name");
            String lastName = queryForRowSet.getString("last_name");

            jwtUser.setId(id);
            jwtUser.setFirstName(fistName);
            jwtUser.setLastName(lastName);

            return jwtUser;

        }

        return null;
    }

    private interface SQL {

        String INSERT_EMAIL_OTP = "INSERT INTO `temp_user`(`email`, `otp`) VALUES ( ? , ? )";
        String VERIFY_OTP = "SELECT `id` FROM `temp_user` WHERE `email` = ? AND `otp` = ? AND attempts < 4";
        String INCREMENT_ATTEMPTS = "UPDATE `temp_user` SET attempts = attempts + 1 WHERE `email` = ?";
        String SET_VERIFIED = "UPDATE `temp_user` SET verified =  1 WHERE `id` = ?";
        String SAVE_USER = "INSERT INTO `user`(`role_id`, `email`, `password`, `first_name`, `last_name`) VALUES ( ? , ? , ?, ? , ? )";
        String VERIFY_NEWUSER = "SELECT `verified` FROM `temp_user` WHERE `id` = ? AND `email` = ? AND `verified` = 1";
        String VERIFY_SIGN_IN = "SELECT * FROM `user` WHERE `email` =  ?   AND `password` =  ?";
    }
}
