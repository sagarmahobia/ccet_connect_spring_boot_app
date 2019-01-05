package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.api.v1.hibernate.entities.Notice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class NoticesRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public NoticesRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Notice> getAllNotices() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Notice> criteria = builder.createQuery(Notice.class);
        Root<Notice> from = criteria.from(Notice.class);//todo test with or without
        criteria.select(from);
        return session.createQuery(criteria).getResultList();

    }

    public void addNotice(Notice notice) {
        Session session = sessionFactory.getCurrentSession();
        session.save(notice);
    }

}
