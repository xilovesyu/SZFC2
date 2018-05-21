package com.xixi.task;

import com.xixi.spider.CanDealSpider;
import com.xixi.spider.DealSpider;
import com.xixi.spider.StockDealSpider;
import com.xixi.util.Property;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**run
 * Created by xijiaxiang on 2017/11/10.
 */
@Component
public class SpiderTask {
    @Scheduled(cron = "0/5 * * * * ? ") //间隔5秒执行
    public void doDealSpiderTask() {
        //System.out.println("test for spring scheduled");
        String dealUrl= Property.getProperty("spiderUrl","dealUrl");
        String canDealUrl=Property.getProperty("spiderUrl","canDealUrl");
        String stockDealUrl=Property.getProperty("spiderUrl","stockDealUrl");
        String canStockDealUrl=Property.getProperty("spiderUrl","canStockDealUrl");
        String preSaleUrl=Property.getProperty("spiderUrl","preSaleUrl");
        //新房
        Spider.create(new DealSpider()).addUrl(dealUrl).thread(1).run();
        Spider.create(new CanDealSpider()).addUrl(canDealUrl).thread(1).run();

        //二手房
        Spider.create(new StockDealSpider()).addUrl(stockDealUrl).thread(1).run();
        StockDealSpider spider=new StockDealSpider();
        spider.saleOrCanSaleLocation="canStockDealLocation";
        Spider.create(spider).addUrl(canStockDealUrl).thread(1).run();

        //预售证信息
    }
}
