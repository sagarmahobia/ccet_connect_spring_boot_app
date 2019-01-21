package com.ccet.backend.api.v1.models.commonmodels.assignments;

import com.ccet.backend.api.v1.hibernate.entities.Assignment;

import java.util.List;

public class Assignments {

    private List<Assignment> assignments;


    public Assignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }


    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

}