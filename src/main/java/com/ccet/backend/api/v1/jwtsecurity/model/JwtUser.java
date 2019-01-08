package com.ccet.backend.api.v1.jwtsecurity.model;

public class JwtUser {
    private int id;
    private String role;

    public JwtUser(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

}
