package com.ccet.backend.api.v1.models.user;

public class UserDetail {
    private int userId;
    private String email;
    private String collegeEmail;
    private String firstName;
    private String lastName;
    private String admissionYear;
    private int admissionSemester;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeEmail() {
        return collegeEmail;
    }

    public void setCollegeEmail(String collegeEmail) {
        this.collegeEmail = collegeEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public int getAdmissionSemester() {
        return admissionSemester;
    }

    public void setAdmissionSemester(int admissionSemester) {
        this.admissionSemester = admissionSemester;
    }
}
