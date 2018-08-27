package com.xixi.controller;

import com.xixi.spider.DealSpider;
import com.xixi.util.Property;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

import javax.management.JMException;

/**路径跳转
 *
 * Created by xijiaxiang on 2017/11/10.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String toindex(){
        return "index";
    }
    @RequestMapping("/SalePage")
    public String toSale(){
        return "SalePage";
    }
    @RequestMapping("/CanSalePage")
    public String toCanSale(){
        return "CanSalePage";
    }
    @RequestMapping("/StockSalePage")
    public String toStockSale(){
        return "StockSalePage";
    }
    @RequestMapping("/StockCanSalePage")
    public String toStockCanSale(){
        return "StockCanSalePage";
    }
    @RequestMapping("/SetModule")
    public String toModule(){
        return "Module";
    }
    @RequestMapping("/ysz")
    public String toYSZ(){
        return "ysz";
    }

    @RequestMapping("/testRun")
    @ResponseBody
    public String testRun() throws JMException {
        String dealUrl= Property.getProperty("spiderUrl","dealUrl");

        //Spider.create(new DealSpider()).addUrl(dealUrl).thread(1).run();
        DealSpider spider=new DealSpider();
        Spider a=Spider.create(spider);
        a.addUrl(dealUrl).thread(1).run();
        Spider.Status status=a.getStatus();
        //SpiderMonitor spiderMonitor = new SpiderMonitor();
        //spiderMonitor.register(a);
        return "success->"+dealUrl+"->status: ";
    }
}
