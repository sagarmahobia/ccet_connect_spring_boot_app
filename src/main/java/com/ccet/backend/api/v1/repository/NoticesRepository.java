package com.ccet.backend.api.v1.repository;


import com.ccet.backend.api.v1.models.commonmodels.notices.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoticesRepository {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public NoticesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Notice> getAllNotices() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL.SQL_FOR_NOTICES);

        List<Notice> notices = new ArrayList<>();

        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String noticeString = sqlRowSet.getString("notice");
            String link = sqlRowSet.getString("link");

            Notice notice = new Notice();
            notice.setId(id);
            notice.setTitle(noticeString);
            notice.setLink(link);

            notices.add(notice);
        }
        return notices;
    }

    private interface SQL {
        String SQL_FOR_NOTICES = "SELECT * FROM `notices`";
    }

}
