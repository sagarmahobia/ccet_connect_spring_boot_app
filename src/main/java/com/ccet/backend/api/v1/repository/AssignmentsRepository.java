package com.ccet.backend.api.v1.repository;

import com.ccet.backend.api.v1.models.commonmodels.assignments.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignmentsRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AssignmentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Assignment> getAssignments(int semester) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL.ASSIGNMENTS, semester);

        List<Assignment> assignments = new ArrayList<>();


        while (sqlRowSet.next()) {

            Assignment assignment = new Assignment();

            int assignmentNo = sqlRowSet.getInt("assignment_no");
            String lastDate = sqlRowSet.getString("last_date");
            String link = sqlRowSet.getString("link");

            assignment.setSemester(semester);
            assignment.setAssignmentNo(assignmentNo);
            assignment.setLastDate(lastDate);
            assignment.setLink(link);

            assignments.add(assignment);
        }

        return assignments;
    }

    private interface SQL {
        String ASSIGNMENTS = "select * from assignments where semester = ?";
    }

}
