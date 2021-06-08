package com.wly.secondkill;

import com.wly.secondkill.services.ActivityHtmlPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThymeleafServiceTest {

    @Autowired
    private ActivityHtmlPageService activityHtmlPageService;

    @Test
    public void createHtmlTest(){
        activityHtmlPageService.createActivityHtml(20);
    }
}
