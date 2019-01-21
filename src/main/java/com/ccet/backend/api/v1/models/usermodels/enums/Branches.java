package com.ccet.backend.api.v1.models.usermodels.enums;

public enum Branches {
    CSE(1),
    Mech(2),
    ETAndT(3),
    ELE(4),
    Other(5);

    private final int branchId;

    Branches(int branchId) {
        this.branchId = branchId;
    }

    public static Branches getBranch(int id) {
        for (Branches branch : Branches.values()) {
            if (branch.getBranchId() == id) {
                return branch;
            }
        }
        return Other;
    }

    private int getBranchId() {
        return this.branchId;
    }

    public static Branches getBranch(String branchString) {
        for (Branches branch : Branches.values()) {
            if (branch.toString().equalsIgnoreCase(branchString)) {
                return branch;
            }
        }
        return Other;
    }

    public String getBranch() {
        return this.toString().toUpperCase();
    }
}
