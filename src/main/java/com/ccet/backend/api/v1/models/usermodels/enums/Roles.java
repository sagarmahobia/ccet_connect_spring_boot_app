/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.models.usermodels.enums;

/**
 * @author SAGAR MAHOBIA
 */
public enum Roles {
    HOD(1),
    Faculty(2),
    Management(3),
    Student(4),
    Admin(5),
    Other(6);
    private final int roleId;

    Roles(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public static Roles getRole(int id) {
        for (Roles role : Roles.values()) {
            if (role.getRoleId() == id) {
                return role;
            }
        }
        return Other;
    }

    public static Roles getRole(String roleString) {
        for (Roles role : Roles.values()) {
            if (role.toString().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        return Other;
    }

    public String getRole() {
        return this.toString().toUpperCase();
    }
}
