package com.xixi.spider;

import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import com.xixi.util.FileUtil;
import com.xixi.util.Property;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓取每日售房量
 * Created by xijiaxiang on 2017/11/10.
 */

public class DealSpider implements PageProcessor{

    private RegionCount regionCount=new RegionCount();
    public RegionCount getRegionCount(){
        return regionCount;
    }
    @Override
    public void process(Page page) {
        Map<String, AreaCount> areas = new HashMap<>();
        //System.out.println(page.toString());
        List<String> trs = page.getHtml().css("#ctl00_MainContent_mytable tr").all();

        for (int i = 1; i < trs.size() - 2; i += 2) {

            AreaCount tempAreaCount=new AreaCount();
            int countnums = 0;
            double countarea = 0.0;
            int zhucount = 0;
            double zhuarea = 0.0;

            String temp1 = trs.get(i).replace("\n", "");
            // System.out.println("temp1"+temp1);
            if (!temp1.contains("总计")) {
                if (temp1.contains("class=\"specalt\"") || temp1.contains("class=\"spec\"")) {
                    //有地区和小计
                    temp1 = temp1.replace("<th class=\"specalt\" rowspan=\"2\">", "")
                            .replace("<th class=\"spec\" rowspan=\"2\">", "")
                            .replace("</th>", ",")
                            .replace("<td>", "")
                            .replace("</td>", ",")
                            .replace("<tr>", "")
                            .replace("</tr>", "").replace("小计,", "").replace("总计,", "");

                    temp1 = temp1.substring(0, temp1.length() - 1);
                    //System.out.println(temp1);
                    String[] temps = temp1.split(",");
                    tempAreaCount.setAreaName(temps[0].trim());
                    countnums = Integer.parseInt(temps[1].trim());
                    countarea = Double.parseDouble(temps[2].trim());

                }
                String temp2 = trs.get(i + 1).replace("\n", "");

                if (temp2.contains("其中：住宅")) {
                    temp2 = temp2.replace("<tr>", "")
                            .replace("<td class=\"alt\">其中：住宅</td>", "")
                            .replace("<td class=\"alt\">", "")
                            .replace("</td>", ",")
                            .replace("</tr>", "");
                    temp2 = temp2.substring(0, temp2.length() - 1);

                    String[] temps = temp2.split(",");
                    zhucount = Integer.parseInt(temps[0].trim());
                    zhuarea = Double.parseDouble(temps[1].trim());


                }
                tempAreaCount.setZhu(zhucount);
                tempAreaCount.setZhu_Area(zhuarea);

                tempAreaCount.setFeiZhu(countnums - zhucount);
                tempAreaCount.setFeiZhu_Area(countarea - zhuarea);
            }
            areas.put(tempAreaCount.getAreaName(),tempAreaCount);
            System.out.println(tempAreaCount.toString());
        }
        regionCount.setAreas(areas);
        System.out.println("start from spider to writing data");
        FileUtil fileUtil=new FileUtil("dealLocation");
        fileUtil.writeDeal(regionCount);
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Accept-Encoding","gzip, deflate")
            .addHeader("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7,ja;q=0.6")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String dealUrl= Property.getProperty("spiderUrl","dealUrl");
        Spider.create(new DealSpider()).addUrl(dealUrl).thread(1).run();
    }
}
