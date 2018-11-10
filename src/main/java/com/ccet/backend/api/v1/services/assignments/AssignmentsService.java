package com.ccet.backend.api.v1.services.assignments;

import com.ccet.backend.api.v1.models.commonmodels.assignments.Assignment;
import com.ccet.backend.api.v1.models.commonmodels.assignments.Assignments;
import com.ccet.backend.api.v1.models.user.UserDetail;
import com.ccet.backend.api.v1.repository.AssignmentsRepository;
import com.ccet.backend.api.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class AssignmentsService {

    private UserRepository userRepository;
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    public AssignmentsService(UserRepository userRepository, AssignmentsRepository assignmentsRepository) {
        this.userRepository = userRepository;
        this.assignmentsRepository = assignmentsRepository;
    }

    public Assignments getAssignments(int user_id) {
        UserDetail userDetail = userRepository.getUserDetail(user_id);
        String admissionYear = userDetail.getAdmissionYear();
        int admissionSemester = userDetail.getAdmissionSemester();

        int currentSemester = getCurrentSemester(Integer.parseInt(admissionYear), admissionSemester);
        List<Assignment> assignments = assignmentsRepository.getAssignments(currentSemester);
        return new Assignments(assignments);
    }

    public int getCurrentSemester(int admissionYear, int admissionSem) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int diff = year - admissionYear;

        if (month <= 6) {
            return diff * 2 + admissionSem - 1;
        } else {
            return diff * 2 + admissionSem;
        }

    }

}
