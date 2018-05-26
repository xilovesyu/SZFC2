package com.xixi.service.impl;

import com.xixi.mapper.PreSaleDao;
import com.xixi.pojo.PreSaleInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Created by xijiaxiang on 2018/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring-mybatis.xml", "classpath:spring-mvc.xml"})
public class BookServiceImplTest {
    @Autowired
    private PreSaleDao preSaleDao;
    @Test
    public void queryById() throws Exception {
        long bookId = 1;

        PreSaleInfo book =  preSaleDao.queryById("");;
        System.out.println(book);
    }

    @Test
    public void insertYSZ() throws Exception {


        PreSaleInfo book =  new PreSaleInfo();
        book.yszNumber="1";
        book.projectName="";
        book.projectAddress="";

        System.out.println(preSaleDao==null);
        preSaleDao.insertOneYSZ(book);

    }
}