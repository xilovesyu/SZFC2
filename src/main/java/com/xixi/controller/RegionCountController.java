package com.xixi.controller;

import com.alibaba.fastjson.JSON;
import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import com.xixi.service.RegionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xijiaxiang on 2017/11/10.
 */
@Controller
public class RegionCountController {
    @Autowired
    private RegionCountService regionCountService;


    @RequestMapping(value="/queryCountTable",produces="application/json;charset=utf-8")
    @ResponseBody
    public String CountBetweenTwoDaysInTable(HttpServletRequest request){
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        start=start.replace("-",".");
        end=end.replace("-",".");
        RegionCount regionCount= null;
        try {
            regionCount = regionCountService.monthdaydeal("dealLocation",start,end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,AreaCount> map= regionCount.getAreas();
        AreaCount[] list=new AreaCount[map.size()];
        int i=0;
        for (String key : map.keySet()){
            list[i]=(map.get(key));
            i++;
        }
        System.out.println("received");
        System.out.println(JSON.toJSONString(list));
        return "{\"code\":0,\"msg\":\"ok\",\"count\":"+list.length+",\"data\":"+JSON.toJSONString(list)+"}";
    }

    @RequestMapping(value="/queryCountTable2",produces="application/json;charset=utf-8")
    @ResponseBody
    public String CountBetweenTwoDaysInTable2(HttpServletRequest request){
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        start=start.replace("-",".");
        end=end.replace("-",".");
        RegionCount regionCount= null;
        try {
            regionCount = regionCountService.monthdaydeal("canDealLocation",start,end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,AreaCount> map= regionCount.getAreas();
        AreaCount[] list=new AreaCount[map.size()];
        int i=0;
        for (String key : map.keySet()){
            list[i]=(map.get(key));
            i++;
        }
        System.out.println("received");
        System.out.println(JSON.toJSONString(list));
        return "{\"code\":0,\"msg\":\"ok\",\"count\":"+list.length+",\"data\":"+JSON.toJSONString(list)+"}";
    }


    //二手房交易的查询(交易量)
    @RequestMapping(value="/queryStockDealCount",produces="application/json;charset=utf-8")
    @ResponseBody
    public String CountStockDealTable(HttpServletRequest request){
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        start=start.replace("-",".");
        end=end.replace("-",".");
        RegionCount regionCount= null;
        try {
            regionCount = regionCountService.monthdaydeal("stockDealLocation",start,end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,AreaCount> map= regionCount.getAreas();
        AreaCount[] list=new AreaCount[map.size()];
        int i=0;
        for (String key : map.keySet()){
            list[i]=(map.get(key));
            i++;
        }
        System.out.println("received");
        System.out.println(JSON.toJSONString(list));
        return "{\"code\":0,\"msg\":\"ok\",\"count\":"+list.length+",\"data\":"+JSON.toJSONString(list)+"}";
    }
    //可以交易总量
    @RequestMapping(value="/queryStockCount",produces="application/json;charset=utf-8")
    @ResponseBody
    public String CountStockDealTable2(HttpServletRequest request){
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        start=start.replace("-",".");
        end=end.replace("-",".");
        RegionCount regionCount= null;
        try {
            regionCount = regionCountService.monthdaydeal("canStockDealLocation",start,end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,AreaCount> map= regionCount.getAreas();
        AreaCount[] list=new AreaCount[map.size()];
        int i=0;
        for (String key : map.keySet()){
            list[i]=(map.get(key));
            i++;
        }
        System.out.println("received");
        System.out.println(JSON.toJSONString(list));
        return "{\"code\":0,\"msg\":\"ok\",\"count\":"+list.length+",\"data\":"+JSON.toJSONString(list)+"}";
    }
}
