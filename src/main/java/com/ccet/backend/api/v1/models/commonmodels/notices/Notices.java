package com.ccet.backend.api.v1.models.commonmodels.notices;

import com.ccet.backend.api.v1.hibernate.entities.Notice;

import java.util.List;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 13:02
 */
public class Notices {
    private List<Notice> notices;

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }
}
