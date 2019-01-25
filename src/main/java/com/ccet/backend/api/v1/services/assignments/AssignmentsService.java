package com.ccet.backend.api.v1.services.assignments;

import com.ccet.backend.api.v1.hibernate.entities.Assignment;
import com.ccet.backend.api.v1.hibernate.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentsService {

    private AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentsService(AssignmentRepository assignmentRepository) {

        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAssignments(int branchId, int semester) {

        return assignmentRepository.getAssignments(branchId, semester);

    }

}
