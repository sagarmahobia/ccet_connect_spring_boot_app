package com.ccet.backend.api.v1.models.commonmodels.assignments;

import com.ccet.backend.api.v1.hibernate.entities.Assignment;

import java.util.List;

public class Assignments {

    private List<Assignment> assignments;

    private int sem;

    private int branchId;

    public Assignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
