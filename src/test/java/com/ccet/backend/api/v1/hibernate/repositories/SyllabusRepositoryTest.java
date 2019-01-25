package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.BackendApplication;
import com.ccet.backend.api.v1.hibernate.entities.Syllabus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class SyllabusRepositoryTest {


    @Autowired
    SyllabusRepository syllabusRepository;


    @Test
    public void test() {

        Syllabus syllabus = new Syllabus();
        syllabus.setSemester(4);
        syllabus.setLink("http://www.youtube.com");
        syllabus.setBranchId(1);

        syllabusRepository.addSyllabus(syllabus);
        List<Syllabus> assignments = syllabusRepository.getSyllabuses(1, 4);
        Assert.assertEquals(1, assignments.size());

    }
}
