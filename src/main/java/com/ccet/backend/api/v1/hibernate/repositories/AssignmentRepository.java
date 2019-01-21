package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.api.v1.hibernate.entities.Assignment;
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
public class AssignmentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AssignmentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addAssignment(Assignment assignment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(assignment);

    }

    public List<Assignment> getAssignments(int branchId, int semester) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Assignment> criteria = builder.createQuery(Assignment.class);
        Root<Assignment> from = criteria.from(Assignment.class);//todo test with or without
        criteria.select(from);
        criteria.where(builder.equal(from.get("semester"), semester), builder.equal(from.get("branchId"), branchId));
        return session.createQuery(criteria).getResultList();

    }


}
