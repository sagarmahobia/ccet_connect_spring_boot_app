package com.ccet.backend.api.v1.services.notices;


import com.ccet.backend.api.v1.hibernate.entities.Notice;
import com.ccet.backend.api.v1.hibernate.repositories.NoticesRepository;
import com.ccet.backend.api.v1.models.commonmodels.notices.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticesService {

    private NoticesRepository noticesRepository;

    @Autowired
    public NoticesService(NoticesRepository noticesRepository) {
        this.noticesRepository = noticesRepository;
    }

    public Notices getNotices() {
        List<Notice> allNotices = noticesRepository.getAllNotices();
        Notices notices = new Notices();
        notices.setNotices(allNotices);
        return notices;
    }

}
