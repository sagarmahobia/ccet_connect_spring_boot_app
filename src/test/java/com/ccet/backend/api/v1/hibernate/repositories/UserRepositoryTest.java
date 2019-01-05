package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.BackendApplication;
import com.ccet.backend.api.v1.hibernate.entities.Otp;
import com.ccet.backend.api.v1.hibernate.entities.User;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private int id;
    private int otpId;

    public void testSaveUser() {
        User user = new User();
        user.setEmail("sagar@gmail.com");
        user.setVerified(false);
        user.setPassWord("Password");
        id = userRepository.saveUser(user);
    }

    public void testSaveOtp() {
        testSaveUser();
        Otp otp = new Otp();
        otp.setUserId(id);
        otp.setOtp("4545");
        otpId = userRepository.saveOtp(otp);
    }

    public void testRemoveOtp() {
        testSaveOtp();
        Otp otp = new Otp();
        otp.setId(otpId);
        userRepository.removeOtp(otp);
    }

    public void testVerifyOtp() {
        testSaveOtp();
        Otp otp2 = new Otp();

        otp2.setId(otpId);
        otp2.setOtp("4546");
        Assert.assertEquals(userRepository.verifyOtp(otp2), -1);

        otp2.setId(otpId + 200);
        otp2.setOtp("4546");
        Assert.assertEquals(userRepository.verifyOtp(otp2), -1);

        Otp otp = new Otp();
        otp.setId(otpId);
        otp.setOtp("4545");
        Assert.assertEquals(userRepository.verifyOtp(otp), id);

    }

    @Test()
    public void testIncrementAttempts() {
        Otp otp = new Otp();
        otp.setUserId(id);
        otp.setOtp("4545");
        otpId = userRepository.saveOtp(otp);

        userRepository.incrementAttempts(otpId);
        userRepository.incrementAttempts(otpId);
        userRepository.incrementAttempts(otpId);

    }

    @Test()
    public void testSetVerified() {
        testSaveUser();
        testSaveUser();
        userRepository.setVerified(id);
    }

    @Test
    public void testVerifySignInCreds() {
        testSaveUser();

        Assert.assertNull(userRepository.verifySignInCreds("wsagar@gmail.com", "wPassword"));
        Assert.assertNull(userRepository.verifySignInCreds("wsagar@gmail.com", "Password"));
        Assert.assertNull(userRepository.verifySignInCreds("sagar@gmail.com", "wPassword"));
        Assert.assertEquals(userRepository.verifySignInCreds("sagar@gmail.com", "Password").getId(), id);

    }

    @Test()
    public void testGetJwtUser() {
        testSaveUser();
        Assert.assertNull(userRepository.getJwtUser(-id));

        JwtUser jwtUser = userRepository.getJwtUser(id);
        Assert.assertEquals(jwtUser.getId(), id);
    }


}