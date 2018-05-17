package com.xixi.task;

import com.xixi.spider.CanDealSpider;
import com.xixi.spider.DealSpider;
import com.xixi.util.Property;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * Created by xijiaxiang on 2017/11/10.
 */
@Component
public class SpiderTask {
    @Scheduled(cron = "0/5 * * * * ? ") //间隔5秒执行
    public void doDealSpiderTask() {
        System.out.println("test for spring scheduled");
        String dealUrl= Property.getProperty("spiderUrl","dealUrl");
        String canDealUrl=Property.getProperty("spiderUrl","canDealUrl");
        Spider.create(new DealSpider()).addUrl(dealUrl).thread(1).run();
        Spider.create(new CanDealSpider()).addUrl(canDealUrl).thread(1).run();
    }
}
