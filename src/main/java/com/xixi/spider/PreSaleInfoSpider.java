package com.xixi.spider;

import com.xixi.mapper.PreSaleDao;
import com.xixi.pojo.PreSaleInfo;
import com.xixi.spider.interceptor.MyOkHttpRetryInterceptor;
import com.xixi.util.Property;
import com.xixi.util.ViewStateInformation;
import okhttp3.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预售证
 * Created by xijiaxiang on 2018/5/14.
 */
@Component
public class PreSaleInfoSpider {
    @Autowired
    private PreSaleDao preSaleDao;

    public int run(String projectName, String regionName, String companyName, String preSaleNumber) {

        String basePreSaleUrl = Property.getProperty("spiderUrl", "preSaleUrl");

        MyOkHttpRetryInterceptor myOkHttpRetryInterceptor = new MyOkHttpRetryInterceptor.Builder()
                .executionCount(3)
                .retryInterval(1000)
                .build();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(myOkHttpRetryInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Request baseSaleInfoRequest = new Request.Builder()
                .url(basePreSaleUrl)
                .build();
        try {
            Response response = httpClient.newCall(baseSaleInfoRequest).execute();
            basePreSaleUrl = response.request().url().toString();

            //解析
            String content = response.body().string();
            Document document = Jsoup.parse(content);
            Element viewStateE = ViewStateInformation.getNetInformation(document, "__VIEWSTATE");
            Element eventValidationE = ViewStateInformation.getNetInformation(document, "__EVENTVALIDATION");

            int count = 1;
            int allPages = 0;
            do {
                //post 请求
                RequestBody body = null;
                FormBody.Builder builder=new FormBody.Builder();
                builder.add("__VIEWSTATE", viewStateE.val());
                builder.add("__EVENTARGUMENT", "");
                builder.add("__LASTFOCUS", "");
                builder.add("__EVENTVALIDATION", eventValidationE.val() == null ? "" : eventValidationE.val());
                builder.add("ctl00$MainContent$txt_Pro", projectName);
                builder.add("ctl00$MainContent$ddl_RD_CODE", regionName);
                builder.add("ctl00$MainContent$txt_Com", companyName);
                builder.add("ctl00$MainContent$txt_ysz", preSaleNumber);
                if (count == 1) {
                    body = builder.add("ctl00$MainContent$bt_select", "查询").build();
                } else {
                    body = builder
                            .add("__EVENTTARGET", "ctl00$MainContent$PageGridView1$ctl12$Next")
                            .add("ctl00$MainContent$PageGridView1$ctl12$PageList", (count - 2) + "").build();
                }
                Request request = new Request.Builder()
                        .url(basePreSaleUrl)
                        .post(body)
                        .build();

                Response response2 = httpClient.newCall(request).execute();

                String initResult = response2.body().string();
                document = Jsoup.parse(initResult);

                if (initResult.contains("数据记录数太多")) {
                    System.out.println("数据记录数太多,分价格处理");
                    return -1;
                } else {
                    //pages
                    if (count == 1) {
                        Element pageCount = document.select("table td[align=left]:contains(共)").first();
                        Pattern pattern = Pattern.compile("共&amp;nbsp\\d*&nbsp;页");
                        Matcher matcher = pattern.matcher(pageCount.toString());
                        if (matcher.find()) {
                            String temp = matcher.group();
                            temp = temp.replace("共&amp;nbsp", "").replace("&nbsp;页", "");
                            allPages = Integer.parseInt(temp);
                        }
                        System.out.println("共" + allPages + "页");
                    }
                    //处理内容

                    Elements onePageHouse = document.select("#ctl00_MainContent_PageGridView1 tr td");
                    Iterator<Element> houseIterator = onePageHouse.iterator();
                    int columnsCount = 0;

                    PreSaleInfo preSaleInfo = new PreSaleInfo();
                    while (houseIterator.hasNext()) {
                        columnsCount++;
                        Element temp = houseIterator.next();
                        if (columnsCount == 3) {
                            preSaleInfo.projectAddress = temp.text();

                            if (preSaleDao.queryById(preSaleInfo.yszNumber) != null) {
                                //System.out.println("exisit this preSaleInfo ");
                            } else {
                                System.out.println("new preSaleInfo ");
                                preSaleDao.insertOneYSZ(preSaleInfo);
                            }

                            columnsCount = 0;
                            preSaleInfo = new PreSaleInfo();
                        }
                        if (columnsCount == 1) {
                            Pattern p = Pattern.compile("window.showModalDialog.*showPro='");
                            Matcher matcher = p.matcher(temp.toString());
                            if (matcher.find()) {
                                String result = matcher.group();
                                result = result.replace("window.showModalDialog('", "").replace("&amp;showPro='", "");
                                String baseDetailUrl = basePreSaleUrl.replaceAll("MITShowList\\.aspx", "");
                                String startDate = new PreSaleDetailInfoSpider(baseDetailUrl + result).run();
                                preSaleInfo.yszStartDate = startDate;
                            }
                            preSaleInfo.yszNumber = temp.text();

                        }
                        if (columnsCount == 2) {
                            preSaleInfo.projectName = temp.text();
                        }
                    }
                }

                //更新ViewState
                viewStateE = document.getElementById("__VIEWSTATE");
                eventValidationE = document.getElementById("__EVENTVALIDATION");


                count++;
            } while ((count <= allPages));

        } catch (IOException e) {

            e.printStackTrace();
        }


        return 0;
    }


    public static void main(String[] args) {
        //@Autowired
        PreSaleInfoSpider test = new PreSaleInfoSpider();
        //
        String projectName = "";
        String regionName = "工业园区";
        String regionNames[] = {"工业园区", "吴中区", "相城区", "高新区", "姑苏区", "吴江区"};
        String companyName = "";
        String ysz = "";

        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring-mvc.xml", "classpath:spring-mybatis.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession session = sqlSessionFactory.openSession();
        PreSaleDao dao = session.getMapper(PreSaleDao.class);
        test.preSaleDao = dao;

        for (String tempRegion : regionNames
                ) {
            test.run(projectName, tempRegion, companyName, ysz);
        }


        session.close();
    }
}

class PreSaleDetailInfoSpider {
    private String url;

    public PreSaleDetailInfoSpider(String url) {
        this.url = url;
    }

    public String run() {
        MyOkHttpRetryInterceptor myOkHttpRetryInterceptor = new MyOkHttpRetryInterceptor.Builder()
                .executionCount(3)
                .retryInterval(1000)
                .build();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(myOkHttpRetryInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Request baseSaleInfoRequest = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = httpClient.newCall(baseSaleInfoRequest).execute();
            url = response.request().url().toString();
            Document document = Jsoup.parse(response.body().string());

            return document.select("#ctl00_MainContent_lb_PP_IDate").first().text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}