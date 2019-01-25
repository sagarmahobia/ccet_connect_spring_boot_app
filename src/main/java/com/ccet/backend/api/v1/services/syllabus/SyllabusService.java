package com.ccet.backend.api.v1.services.syllabus;


import com.ccet.backend.api.v1.hibernate.entities.Syllabus;
import com.ccet.backend.api.v1.hibernate.repositories.SyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyllabusService {

    private SyllabusRepository syllabusRepository;

    @Autowired
    public SyllabusService(SyllabusRepository syllabusRepository) {

        this.syllabusRepository = syllabusRepository;
    }

    public List<Syllabus> getSyllabusList(int branchId, int semester) {
        return syllabusRepository.getSyllabuses(branchId, semester);
    }
}
