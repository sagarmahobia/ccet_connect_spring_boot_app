package com.ccet.backend.api.v1.hibernate.repositories;

import com.ccet.backend.BackendApplication;
import com.ccet.backend.api.v1.hibernate.entities.Notice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class NoticesRepositoryTest {

    @Autowired
    NoticesRepository noticesRepository;

    @Test
    public void testNotice() {


        Notice notice = new Notice();
        notice.setLink("link");
        notice.setTitle("Notice sample 1");

        Notice notice2 = new Notice();
        notice2.setLink("link");
        notice2.setTitle("Notice sample 1");


        noticesRepository.addNotice(notice);
        noticesRepository.addNotice(notice2);
        List<Notice> allNotices = noticesRepository.getAllNotices();
        Assert.assertEquals(2, allNotices.size());
    }


}