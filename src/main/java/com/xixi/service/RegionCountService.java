package com.xixi.service;

import com.alibaba.fastjson.JSON;
import com.xixi.pojo.RegionCount;
import com.xixi.spider.CanDealSpider;
import com.xixi.spider.DealSpider;
import com.xixi.util.MyTimer;
import com.xixi.util.Property;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.swing.plaf.synth.Region;
import java.io.*;

/**
 * Created by xijiaxiang on 2017/11/10.
 */
@Service
public class RegionCountService {
    public RegionCount nowdaydeal() {
        DealSpider dealSpider = new DealSpider();
        String initUrl = Property.getProperty("spiderUrl", "dealUrl");
        System.out.println(initUrl);
        Spider daydealspider = Spider.create(dealSpider).addUrl(initUrl);
        daydealspider.run();
        daydealspider.stop();
        return dealSpider.getRegionCount();
    }

    public RegionCount cansaleDeal() {
        CanDealSpider spider = new CanDealSpider();
        String initUrl = Property.getProperty("spiderUrl", "canDealUrl");
        Spider daydealspider = Spider.create(spider).addUrl(initUrl);
        daydealspider.run();
        daydealspider.stop();
        return spider.getDayDeal();
    }

    public RegionCount monthdaydeal(String fileLocationKey, String startweek, String endweek) throws IOException {
        RegionCount dayDeal = new RegionCount();
        if (startweek.split("\\.").length == 2 && endweek.split("\\.").length == 2) {
            //5.30 -> 2017.5.30
            startweek = MyTimer.getShangHaiYear() + "." + startweek;
            endweek = MyTimer.getShangHaiYear() + "." + endweek;
            startweek = startweek.replace(".", "-");
            endweek = endweek.replace(".", "-");
        }else{
            startweek=startweek.replace(".","-");
            endweek=endweek.replace(".","-");
        }
        //列出这两个时间点的所有数据文件
        String FileLocation = Property.getProperty("fileLocation", fileLocationKey);
        File file = new File(FileLocation);
        if (file.isDirectory()) {
            MyFileFilter fileFilter = new MyFileFilter(startweek, endweek);
            File[] files = file.listFiles(fileFilter);
            for (File tempfile : files
                    ) {
                //处理每一个文件
                System.out.println(tempfile.getName());
                BufferedReader reader = new BufferedReader(new InputStreamReader((new FileInputStream(tempfile)), "utf-8"));
                String line = "";
                line = reader.readLine();
                System.out.println("line" + line);
                reader.close();
                RegionCount group = JSON.parseObject(line, RegionCount.class);
                System.out.println(group);
                dayDeal.add(group);
            }
        }

        return dayDeal;
    }
}

class MyFileFilter implements FileFilter {
    public String starttime;
    public String endtime;

    public MyFileFilter(String starttime, String endtime) {
        this.starttime = starttime;
        this.endtime = endtime;
    }


    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        if (name.split("\\.").length == 2) {
            String suffix = name.split("\\.")[1];
            return MyTimer.isBetweenTwoDays(starttime, endtime, name.substring(0, name.length() - suffix.length() - 1));
        } else {
            return false;
        }
    }
}
