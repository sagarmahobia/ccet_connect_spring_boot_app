package com.ccet.backend.api.v1.models.commonmodels.assignments;

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
