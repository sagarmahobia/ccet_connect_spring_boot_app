/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.api.v1.exceptions.InvalidInputException;
import com.ccet.backend.api.v1.hibernate.entities.Otp;
import com.ccet.backend.api.v1.hibernate.entities.User;
import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import com.ccet.backend.api.v1.models.usermodels.enums.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author SAGAR MAHOBIA
 */
@Repository
@Transactional
public class UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(user);

    }

    public int saveOtp(Otp otp) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(otp);
    }

    public int verifyOtp(Otp otp) {

        Session session = sessionFactory.getCurrentSession();
        Otp dbOtp = session.get(Otp.class, otp.getId());
        if (dbOtp == null) {
            return -1;
        }
        if (dbOtp.getOtp().equalsIgnoreCase(otp.getOtp())) {
            return dbOtp.getUserId();
        } else {
            return -1;
        }
    }

    public void incrementAttempts(int otpId) {
        Session session = sessionFactory.getCurrentSession();
        Otp dbOtp = session.get(Otp.class, otpId);
        if (dbOtp != null) {
            dbOtp.setAttempts(dbOtp.getAttempts() + 1);
            session.save(dbOtp);
        } else {
            throw new InvalidInputException();
        }

    }

    public void setVerifiedEmail(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        user.setVerifiedEmail(true);
        session.save(user);
    }

    public JwtUser verifySignInCreds(String email, String password) {

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);//todo test with or without
        criteria.where(builder.equal(from.get("email"), email), builder.equal(from.get("passWord"), password));
        Query<User> query = session.createQuery(criteria);
        List<User> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        User result = resultList.get(0);
        if (!result.isVerifiedEmail()) {
            return null;
        }
        int id = result.getId();
        String role = Roles.getRole(result.getRoleId()).getRole();

        return new JwtUser(id, role);

    }

    public User getUserDetail(int userId) {
        return sessionFactory.getCurrentSession().get(User.class, userId);
    }

    public JwtUser getJwtUser(int userId) {

        User userDetail = getUserDetail(userId);
        if (userDetail != null) {
            int id = userDetail.getId();
            String role = Roles.getRole(userDetail.getRoleId()).getRole();
            return new JwtUser(id, role);
        }
        return null;
    }

    public void removeOtp(Otp otp) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(otp);

    }
}
