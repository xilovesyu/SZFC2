package com.xixi.spider;

import com.xixi.pojo.PreSaleInfo;
import com.xixi.spider.interceptor.MyOkHttpRetryInterceptor;
import com.xixi.util.Property;
import com.xixi.util.ViewStateInformation;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 可售房屋情况
 * Created by xijiaxiang on 2018/5/14.
 */
public class PreSaleInfoSpider {
    public int run(String projectName, String regionName, String companyName, String preSaleNumber) {

        String basePreSaleUrl= Property.getProperty("spiderUrl","preSaleUrl");

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
            //System.out.println(baseSaleInfoPage);

            //解析
            String content = response.body().string();
            Document document = Jsoup.parse(content);
            Element viewStateE = ViewStateInformation.getNetInformation(document, "__VIEWSTATE");
            //Element lastFocusE = ViewStateInformation.getNetInformation(document, "__LASTFOCUS");
            //Element eventArgumentE = ViewStateInformation.getNetInformation(document, "__EVENTARGUMENT");
            //Element eventTargetE=ViewStateInformation.getNetInformation(document,"__EVENTTARGET");
            Element eventValidationE=ViewStateInformation.getNetInformation(document,"__EVENTVALIDATION");

            int count = 1;
            int allPages = 0;
            do {
                //post 请求
                RequestBody body = null;
                if (count == 1) {
                    body = new FormBody.Builder()
                            .add("__VIEWSTATE", viewStateE.val())
                            .add("__EVENTTARGET", "")
                            .add("__EVENTARGUMENT", "")
                            .add("__LASTFOCUS","")
                            .add("__EVENTVALIDATION",eventValidationE.val()==null?"":eventValidationE.val())
                            .add("ctl00$MainContent$txt_Pro", projectName)
                            .add("ctl00$MainContent$ddl_RD_CODE", regionName)
                            .add("ctl00$MainContent$txt_Com", companyName)
                            .add("ctl00$MainContent$txt_ysz",preSaleNumber)

                            .add("ctl00$MainContent$bt_select", "查询").build();
                } else {
                    body = new FormBody.Builder()
                            .add("__VIEWSTATE", viewStateE.val())
                            .add("__EVENTTARGET", "ctl00$MainContent$PageGridView1$ctl12$Next")
                            .add("__EVENTARGUMENT", "")
                            .add("__LASTFOCUS","")
                            .add("__EVENTVALIDATION",eventValidationE.val()==null?"":eventValidationE.val())
                            .add("ctl00$MainContent$txt_Pro", projectName)
                            .add("ctl00$MainContent$ddl_RD_CODE", regionName)
                            .add("ctl00$MainContent$txt_Com", companyName)
                            .add("ctl00$MainContent$txt_ysz",preSaleNumber)
                            .add("ctl00$MainContent$PageGridView1$ctl12$PageList", (count - 2) + "").build();
                }
                Request request = new Request.Builder()
                        .url(basePreSaleUrl)
                        .post(body)
                        .build();

                Response response2 = httpClient.newCall(request).execute();

                String initResult = response2.body().string();
                document = Jsoup.parse(initResult);

                if(initResult.contains("数据记录数太多")){
                    System.out.println("数据记录数太多,分价格处理");
                    return -1;
                }
                else {
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
                        //System.out.println(pageCount.toString());
                        System.out.println("共"+allPages+"页");
                    }
                    //处理内容

                    /*Elements onePageHouse = document.select("#ctl00_MainContent_PageGridView1 tr td");
                    Iterator<Element> houseIterator = onePageHouse.iterator();
                    int columnsCount = 0;

                    while (houseIterator.hasNext()) {
                        columnsCount++;
                        Element temp = houseIterator.next();
                        System.out.print(temp.text() + ",");
                        if (columnsCount == 6) {
                            System.out.println();
                            columnsCount = 0;
                        }
                    }*/
                    Elements onePageHouse=document.select("#ctl00_MainContent_PageGridView1 tr td");
                    Iterator<Element> houseIterator = onePageHouse.iterator();
                    int columnsCount = 0;

                    PreSaleInfo preSaleInfo=new PreSaleInfo();
                    while (houseIterator.hasNext()) {
                        columnsCount++;
                        Element temp = houseIterator.next();
                        System.out.print(temp.text() + ",");
                        if (columnsCount == 3) {
                            System.out.println();
                            columnsCount = 0;
                        }

                    }
                   // System.out.println(document.html().toString());;
                }

                //更新ViewState
                viewStateE = document.getElementById("__VIEWSTATE");
                eventValidationE = document.getElementById("__EVENTVALIDATION");

                System.out.println(count+"页完成了");

                count++;
            } while ((count <= allPages));

        } catch (IOException e) {

            e.printStackTrace();
        }


        return 0;
    }


    public static void main(String[] args) {
        PreSaleInfoSpider test = new PreSaleInfoSpider();
        //
        String projectName = "";
        String regionName = "工业园区";
        //工业园区,吴中区,相城区,高新区,姑苏区,吴江区
        String companyName = "";
       String ysz="";
        test.run(projectName, regionName, companyName,  ysz);
    }
}
