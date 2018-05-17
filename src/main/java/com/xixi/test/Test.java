package com.xixi.test;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**测试
 * Created by xijiaxiang on 2017/11/10.
 */
@Component
public class Test {
    @Scheduled(cron = "0/5 * * * * ? ") //间隔5秒执行
    public void taskCycle() {
        System.out.println("test for spring scheduled");
    }
}
