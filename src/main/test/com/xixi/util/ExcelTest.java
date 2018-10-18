package com.xixi.util;

import com.xixi.pojo.RegionCount;
import com.xixi.service.RegionCountService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xijiaxiang on 2018/8/27.
 */
public class ExcelTest {
    @Test
    public void createExcel() throws Exception {
        RegionCount regionCount=new RegionCountService().nowdaydeal();
        System.out.println(regionCount);
        Excel.createExcel("excel.xls",regionCount,"D://",false);

    }

    @Test
    public void createExcel2() throws Exception {
    }

}