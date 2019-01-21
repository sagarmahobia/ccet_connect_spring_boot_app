package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.exceptions.InvalidInputException;
import com.ccet.backend.api.v1.hibernate.entities.Assignment;
import com.ccet.backend.api.v1.models.commonmodels.assignments.Assignments;
import com.ccet.backend.api.v1.services.assignments.AssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SAGAR MAHOBIA
 */

@RestController
public class AssignmentsController {

    private AssignmentsService assignmentsService;

    @Autowired
    public AssignmentsController(AssignmentsService assignmentsService) {
        this.assignmentsService = assignmentsService;
    }

    @RequestMapping(path = "/api/v1/public/assignments", method = RequestMethod.POST)
    public Assignments getAssignmentsBySemAndNo(@RequestBody Assignment assignments) {

        if (assignments.getSemester() == 0 || assignments.getBranchId() == 0) {
            throw new InvalidInputException();
        }

        return new Assignments(assignmentsService.getAssignments(assignments.getBranchId(), assignments.getSemester()));

    }
}
