package com.ccet.backend.api.v1.controllers;

import com.ccet.backend.api.v1.models.commonmodels.notices.Notices;
import com.ccet.backend.api.v1.services.notices.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SAGAR MAHOBIA
 */

@RestController
public class NoticesController {

    private NoticesService noticesService;

    @Autowired
    public NoticesController(NoticesService noticesService) {
        this.noticesService = noticesService;
    }

    @RequestMapping(path = "/api/v1/protected/notices")
    public Notices getNotices() {
        return noticesService.getNotices();
    }


}


