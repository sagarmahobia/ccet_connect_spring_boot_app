package com.ccet.backend.api.v1.controllers;


import com.ccet.backend.api.v1.exceptions.InvalidInputException;
import com.ccet.backend.api.v1.hibernate.entities.Syllabus;
import com.ccet.backend.api.v1.models.commonmodels.syllabus.Syllabuses;
import com.ccet.backend.api.v1.services.syllabus.SyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SyllabusController {


    private SyllabusService syllabusService;

    @Autowired
    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    @RequestMapping(path = "/api/v1/public/syllabuses", method = RequestMethod.POST)
    public Syllabuses getSyllabus(@RequestBody Syllabus syllabus) {

        if (syllabus.getSemester() == 0 || syllabus.getBranchId() == 0) {
            throw new InvalidInputException();
        }

        List<Syllabus> syllabusList = syllabusService.getSyllabusList(syllabus.getBranchId(), syllabus.getSemester());
        return new Syllabuses(syllabusList);

    }
}
