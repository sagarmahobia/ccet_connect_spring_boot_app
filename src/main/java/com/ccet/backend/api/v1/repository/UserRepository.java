/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.repository;

import com.ccet.backend.api.v1.exceptions.EmailAlreadyUsedException;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.commonmodels.SignUpModel;
import com.ccet.backend.api.v1.models.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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

    private interface SQL {

        String SIGN_UP = "INSERT INTO `user`" +
                " (`email`, " +
                "  `first_name`," +
                "  `last_name`," +
                "  `admission_year`," +
                "  `admission_semester`," +
                "  `password` )" +
                " VALUES ( ? , ? , ? , ? , ? , ?) ";

        String SAVE_OTP = "INSERT INTO `otp` ( `user_id`, `otp`) VALUES ( ? , ? );";

        String VERIFY_OTP = "Select `user`.`user_id` FROM `user` INNER JOIN `otp` ON `user`.`user_id` = `otp`.`user_id`" +
                " WHERE `otp`.`otp` = ? AND `user`.`email` = ? ";

        String INCREMENT_ATTEMPTS = "UPDATE otp INNER JOIN `user` ON `user`.user_id = otp.user_id " +
                "SET otp.attempts = otp.attempts + 1 WHERE `user`.email = ? ";

        String SET_VERIFIED = "UPDATE `user` SET `user`.verified = 1 WHERE `user_id` = ?";

        String VERIFY_SIGN_IN = "SELECT * FROM `user` WHERE `email` =  ?   AND `password` =  ?";

        String USER_DETAIL = "SELECT * FROM `user` WHERE `user_id` = ?";


    }

    public UserDetail getUserDetail(int user_id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL.USER_DETAIL, user_id);
        if (sqlRowSet.first()) {

            String first_name = sqlRowSet.getString("first_name");
            String last_name = sqlRowSet.getString("last_name");
            String email = sqlRowSet.getString("email");
            String collegeEmail = sqlRowSet.getString("college_email");
            String admissionYear = sqlRowSet.getString("admission_year");
            int admissionSemester = sqlRowSet.getInt("admission_semester");

            UserDetail userDetail = new UserDetail();
            userDetail.setUserId(user_id);
            userDetail.setFirstName(first_name);
            userDetail.setLastName(last_name);
            userDetail.setEmail(email);
            userDetail.setCollegeEmail(collegeEmail);
            userDetail.setAdmissionYear(admissionYear);
            userDetail.setAdmissionSemester(admissionSemester);

            return userDetail;
        } else {
            return null;
        }
    }

    public JwtUser getJwtUser(int id) {

        UserDetail userDetail = getUserDetail(id);
        if (userDetail != null) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(id);
            jwtUser.setFirstName(userDetail.getFirstName());
            jwtUser.setLastName(userDetail.getLastName());
            return jwtUser;
        }
        return null;
    }

    public int verifyOtp(String email, String otp) {

        SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(SQL.VERIFY_OTP, otp, email);
        if (queryForRowSet.first()) {
            return queryForRowSet.getInt("user_id");
        } else {
            return -1;
        }
    }

    public void incrementAttempts(String email) {
        jdbcTemplate.update(SQL.INCREMENT_ATTEMPTS, email);
    }

    public void setVerified(int userId) {
        jdbcTemplate.update(SQL.SET_VERIFIED, userId);
    }

    public JwtUser verifySignInCreds(String email, String password) {
        SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(SQL.VERIFY_SIGN_IN, email, password);
        if (queryForRowSet.first()) {

            JwtUser jwtUser = new JwtUser();

            int id = queryForRowSet.getInt("user_id");
            String fistName = queryForRowSet.getString("first_name");
            String lastName = queryForRowSet.getString("last_name");

            jwtUser.setId(id);
            jwtUser.setFirstName(fistName);
            jwtUser.setLastName(lastName);

            return jwtUser;
        }

        return null;
    }

    public int saveUser(SignUpModel signUpModel) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update((conn) -> {

                PreparedStatement pstmt = conn.prepareStatement(SQL.SIGN_UP, Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, signUpModel.getEmail());
                pstmt.setString(2, signUpModel.getFirstName());
                pstmt.setString(3, signUpModel.getLastName());
                pstmt.setString(4, signUpModel.getAdmissionYear());
                pstmt.setInt(5, signUpModel.getAdmissionSemester());
                pstmt.setString(6, signUpModel.getPassWord());
                return pstmt;
            }, keyHolder);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyUsedException();
        }
        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : -1;
    }

    public void saveOtp(int userId, String otp) {
        jdbcTemplate.update(SQL.SAVE_OTP, userId, otp);
    }
}
