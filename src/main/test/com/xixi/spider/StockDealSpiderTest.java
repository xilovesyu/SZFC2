package com.xixi.spider;

import com.xixi.util.Property;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

import static org.junit.Assert.*;

/**
 * Created by xijiaxiang on 2018/5/17.
 */
public class StockDealSpiderTest {
    @Test
    public void testGetPage(){
        String stockDealUrl= Property.getProperty("spiderUrl","stockDealUrl");
        Spider.create(new StockDealSpider()).addUrl(stockDealUrl).thread(1).run();
    }
}