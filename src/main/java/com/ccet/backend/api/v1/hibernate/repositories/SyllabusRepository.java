package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.api.v1.hibernate.entities.Syllabus;
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
public class SyllabusRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public SyllabusRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addSyllabus(Syllabus syllabus) {
        Session session = sessionFactory.getCurrentSession();
        session.save(syllabus);

    }

    public List<Syllabus> getSyllabuses(int branchId, int semester) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Syllabus> criteria = builder.createQuery(Syllabus.class);
        Root<Syllabus> from = criteria.from(Syllabus.class);//todo test with or without
        criteria.select(from);
        criteria.where(builder.equal(from.get("semester"), semester), builder.equal(from.get("branchId"), branchId));
        return session.createQuery(criteria).getResultList();

    }

}
