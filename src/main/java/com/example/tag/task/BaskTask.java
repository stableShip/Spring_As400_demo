package com.example.tag.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public abstract class BaskTask {
    public String name;

    public String nameZh;

    private void selfCheck() throws Exception {
        if (StringUtils.isEmpty( this.nameZh ) || StringUtils.isEmpty( this.name )) {
            throw new Exception( "任务名称不能为空" );
        }
    }

    public void process() {
        try {
            // todo lock
            this.selfCheck();
            SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" );
            log.info( MessageFormat.format( "{0} {1} 开始运行", dateFormat.format( new Date() ), this.nameZh ) );
            this.content();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract void content() throws Exception;
}
