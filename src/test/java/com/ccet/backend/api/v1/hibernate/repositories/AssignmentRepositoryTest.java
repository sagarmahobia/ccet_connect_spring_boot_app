package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.BackendApplication;
import com.ccet.backend.api.v1.hibernate.entities.Assignment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class AssignmentRepositoryTest {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Test
    public void getAssignment() {

        Assignment assignment = new Assignment();
        assignment.setSemester(4);
        assignment.setAssignmentNo(1);
        assignment.setLastDate("fdsfsadf");
        assignment.setLink("fdsfsdf");

        assignmentRepository.addAssignment(assignment);
        List<Assignment> assignments = assignmentRepository.getAssignments(4);
        Assert.assertEquals(1, assignments.size());

    }


}