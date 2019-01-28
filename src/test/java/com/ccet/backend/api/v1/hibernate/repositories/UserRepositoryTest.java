package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.BackendApplication;
import com.ccet.backend.api.v1.hibernate.entities.Otp;
import com.ccet.backend.api.v1.hibernate.entities.User;
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
    private User user;
    private int otpId;

    public void testSaveUser() {
        User user = new User();
        user.setEmail("sagar@gmail.com");
        user.setVerifiedEmail(false);
        user.setPassWord("Password");
        userRepository.saveUser(user);
        id = user.getId();
        this.user = user;
    }

    public void testSaveOtp() {
        testSaveUser();
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtp("4545");
        otpId = userRepository.saveOtp(otp);
    }

    //working
    public void testRemoveOtp() {
        testSaveOtp();
        Otp otp = new Otp();
        otp.setId(otpId);
        userRepository.removeOtp(otp);
    }

    //done
    public void testVerifyOtp() {
        testSaveOtp();
        Otp otp2 = new Otp();

        otp2.setId(otpId);
        otp2.setOtp("4546");
        Assert.assertNull(userRepository.verifyOtp(otp2));

        otp2.setId(otpId + 200);
        otp2.setOtp("4546");
        Assert.assertNull(userRepository.verifyOtp(otp2));

        Otp otp = new Otp();
        otp.setId(otpId);
        otp.setOtp("4545");
        Assert.assertNotNull(userRepository.verifyOtp(otp));

    }

    @Test
    public void testIncrementAttempts() {
        Otp otp = new Otp();
        otp.setUser(user);
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
        userRepository.setVerifiedEmail(user);
    }

    @Test
    public void testVerifySignInCreds() {
        testSaveUser();
        userRepository.setVerifiedEmail(user);

        Assert.assertNull(userRepository.verifySignInCreds("wsagar@gmail.com", "wPassword"));
        Assert.assertNull(userRepository.verifySignInCreds("wsagar@gmail.com", "Password"));
        Assert.assertNull(userRepository.verifySignInCreds("sagar@gmail.com", "wPassword"));
        Assert.assertEquals(userRepository.verifySignInCreds("sagar@gmail.com", "Password").getId(), user.getId());

    }

    @Test()
    public void testGetJwtUser() {
        testVerifyOtp();
        Assert.assertNull(userRepository.getUserDetail(-id));

        User jwtUser = userRepository.getUserDetail(id);
        Assert.assertEquals(jwtUser.getId(), id);
    }


}