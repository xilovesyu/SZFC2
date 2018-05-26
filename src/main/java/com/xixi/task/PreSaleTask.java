package com.xixi.task;

import com.xixi.spider.PreSaleInfoSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**预售证的定时任务
 * Created by xijiaxiang on 2018/5/22.
 */
@Component
public class PreSaleTask {
    @Autowired
    PreSaleInfoSpider spider=null;

    @Scheduled(cron = "0 0/20 * * * ? ") //间隔执行
    public void doPreSaleTask() {
        //预售证信息

        //工业园区,吴中区,相城区,高新区,姑苏区,吴江区
        String[] regions={"工业园区","吴中区","相城区","高新区","吴江区","姑苏区"};
        for (String region:regions
                ) {
            spider.run("",region,"","");
        }

    }
}
