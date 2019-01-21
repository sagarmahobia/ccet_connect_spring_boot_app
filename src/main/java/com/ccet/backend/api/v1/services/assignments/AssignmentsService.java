package com.ccet.backend.api.v1.services.assignments;

import com.ccet.backend.api.v1.hibernate.entities.Assignment;
import com.ccet.backend.api.v1.hibernate.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    public int getCurrentSemester(int admissionYear, int admissionSem) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int diff = year - admissionYear;

        if (month <= 6) {
            return diff * 2 + admissionSem - 1;
        } else {
            return diff * 2 + admissionSem;
        }

    }

}
