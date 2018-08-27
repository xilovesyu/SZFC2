package com.xixi.spider;

import com.alibaba.fastjson.JSON;
import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import com.xixi.util.FileUtil;
import com.xixi.util.MyTimer;
import com.xixi.util.Property;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xijiaxiang on 2017/11/10.
 */
public class CanDealSpider implements PageProcessor {

    private RegionCount dayDeal = new RegionCount();//每日可售
   // private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public RegionCount getDayDeal() {
        return dayDeal;
    }

    @Override
    public void process(Page page) {
        Map<String,AreaCount> maps=new HashMap<>();//
        List<String> tables = page.getHtml().css("#ctl00_MainContent_Panel1 > table ").all();
        for (int i = 0; i < tables.size()-1; i++) {
            // System.out.println("----------------------------");

            org.jsoup.nodes.Document document = Jsoup.parse(tables.get(i));
            Elements elements = document.getElementsByTag("td");
            AreaCount temparea = new AreaCount();
            int tempzhu=0;int tempzong=0;
            double tempzhuarea=0;double tempzongarea=0;
            for (int j=0;j<elements.size();j++){
                String text=elements.get(j).text();
                if(j==0){
                    temparea.setAreaName(text.replace(" ","").trim());
                }else if(j==3){
                    tempzong=Integer.parseInt(text.replace("套",""));
                }else if(j==5){
                    tempzongarea=Double.parseDouble(text.replace("平方米",""));
                }else if(j==9){
                    tempzhu=Integer.parseInt(text.replace("套",""));
                }else if(j==11){
                    tempzhuarea=Double.parseDouble(text.replace("平方米",""));
                }
            }
            temparea.setZhu(tempzhu);
            temparea.setZhu_Area(tempzhuarea);
            temparea.setFeiZhu(tempzong-tempzhu);
            temparea.setFeiZhu_Area(tempzongarea-tempzhuarea);

            maps.put(temparea.getAreaName(),temparea);
        }
        dayDeal.setAreas(maps);
        //System.out.println(html);

//        File file = new File( Property.getProperty("fileLocation","candealLocation")+ MyTimer.getShangHaiTime() + ".txt");
//
//        if(file.exists()){
//            file.delete();
//            try {
//                file.createNewFile();
//                file.setLastModified(Instant.now().toEpochMilli());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        BufferedWriter writer = null;
//        try {
//
//            writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
//
//            writer.write(JSON.toJSONString(dayDeal));
//            writer.newLine();
//            writer.close();
//        } catch (Exception e) {
//
//        }
        FileUtil fileUtil=new FileUtil("canDealLocation");
        fileUtil.writeDeal(dayDeal);
    }

    @Override
    public Site getSite() {
        return site;
    }
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Accept-Encoding","gzip, deflate")
            .addHeader("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7,ja;q=0.6")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");

    public static void main(String[] args) throws IOException {
        Properties por = new Properties();
        por.load(DealSpider.class.getClassLoader().getResourceAsStream("spider.properties"));
        CanDealSpider spider=new CanDealSpider();

        Spider.create(spider).addUrl(por.getProperty("candealurl")).thread(1).run();

        System.out.println(spider.getDayDeal().toString());

    }
}
