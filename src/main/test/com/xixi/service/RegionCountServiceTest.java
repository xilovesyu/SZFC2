package com.xixi.service;

import com.alibaba.fastjson.JSON;
import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by xijiaxiang on 2017/11/10.
 */
public class RegionCountServiceTest {
    @Test
    public void monthdaydeal() throws Exception {
        RegionCountService regionCountService=new RegionCountService();
        RegionCount regionCount=regionCountService.monthdaydeal("dealLocation","2017-09-14","2017-09-15");
        Map<String,AreaCount> map= regionCount.getAreas();
        AreaCount[] list=new AreaCount[map.size()];
        int i=0;
        for (String key : map.keySet()){
            list[i]=(map.get(key));
            i++;
        }
        System.out.println(JSON.toJSONString(list));
    }

}