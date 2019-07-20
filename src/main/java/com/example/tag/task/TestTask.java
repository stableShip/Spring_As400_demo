package com.example.tag.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestTask extends BaskTask {

    public TestTask() {
        this.nameZh = "测试任务";
        this.name = "testTask";
    }

    //    @Scheduled(cron = "* */1 * * * *")
    public void process() {
        super.process();
    }

    @Override
    void content() {
        log.info( "run testTask Content" );
    }

}
