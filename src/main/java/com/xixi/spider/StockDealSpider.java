package com.xixi.spider;

import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import com.xixi.util.FileUtil;
import com.xixi.util.IteratorUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * 抓取每日售房量
 * Created by xijiaxiang on 2017/11/10.
 */

public class StockDealSpider implements PageProcessor{

    private RegionCount regionCount=new RegionCount();
    public RegionCount getRegionCount(){
        return regionCount;
    }
    public String saleOrCanSaleLocation="stockDealLocation";

    @Override
    public void process(Page page) {
        Map<String, AreaCount> areas = new HashMap<>();

        //System.out.println(page.getHtml().toString());

        Document document=Jsoup.parse(page.getHtml().toString());
        Elements trElements = document.select("#ctl00_ContentPlaceHolder1_mytable tr");

        Iterator<Element> trIterator=trElements.iterator();
        List<Element> trs=IteratorUtil.copyIterator(trIterator);

        for (int i = 1; i < trs.size() - 2; i += 2) {

            Element firstRow=trs.get(i);
            Element secondRow=trs.get(i + 1);

            String regionName=firstRow.getElementsByTag("th").first().text();
            int countAll=0;
            double countAreaAll=0.0;
            try {
                countAll = Integer.parseInt(firstRow.getElementsByTag("td").get(1).text());
                countAreaAll=Double.parseDouble(firstRow.getElementsByTag("td").last().text());
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
            int countZhu=0;
            double countAreaZhu=0.0;
            try {
                countZhu = Integer.parseInt(secondRow.getElementsByTag("td").get(1).text());
                countAreaZhu=Double.parseDouble(secondRow.getElementsByTag("td").last().text());
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
            AreaCount tempAreaCount=new AreaCount();
            tempAreaCount.setAreaName(regionName);
            tempAreaCount.setZhu(countZhu);
            tempAreaCount.setZhu_Area(countAreaZhu);

            tempAreaCount.setFeiZhu(countAll - countZhu);
            tempAreaCount.setFeiZhu_Area(countAreaAll - countAreaZhu);


            areas.put(tempAreaCount.getAreaName(),tempAreaCount);
        }
        regionCount.setAreas(areas);


        FileUtil fileUtil=new FileUtil(saleOrCanSaleLocation);
        fileUtil.writeDeal(regionCount);
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

    }
}
