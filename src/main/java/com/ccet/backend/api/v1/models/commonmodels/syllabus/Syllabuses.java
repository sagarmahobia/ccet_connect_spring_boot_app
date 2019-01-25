package com.ccet.backend.api.v1.models.commonmodels.syllabus;

import com.ccet.backend.api.v1.hibernate.entities.Syllabus;

import java.util.List;

public class Syllabuses {

    private List<Syllabus> syllabuses;


    public Syllabuses(List<Syllabus> syllabusList) {
        this.syllabuses = syllabusList;
    }


    public List<Syllabus> getSyllabuses() {
        return syllabuses;
    }

    public void setAssignments(List<Syllabus> syllabuses) {
        this.syllabuses = syllabuses;
    }
}
