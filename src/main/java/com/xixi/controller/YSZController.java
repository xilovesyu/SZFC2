package com.xixi.controller;

import com.alibaba.fastjson.JSON;
import com.xixi.mapper.PreSaleDao;
import com.xixi.pojo.PreSaleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**预售证的控制器
 * Created by xijiaxiang on 2018/5/26.
 */
@Controller
public class YSZController {
    @Autowired
    private PreSaleDao preSaleDao;

    @RequestMapping(value="/queryYSZ",produces="application/json;charset=utf-8")
    @ResponseBody
    public String  getYSZ(HttpServletRequest request){
        String pageNumber=request.getParameter("page");
        String limitNumber=request.getParameter("limit");
        int page=1;
        int limit=30;
        try {
             page = Integer.parseInt(pageNumber);
             limit = Integer.parseInt(limitNumber);
        }catch (NumberFormatException e){
            System.out.println("number format error ");
        }

        int count=preSaleDao.queryCount();

        List<PreSaleInfo> preSaleInfoList=preSaleDao.queryAll((page-1)*limit,limit);

        return "{\"code\":0,\"msg\":\"ok\",\"count\":"+count+",\"data\":"+ JSON.toJSONString(preSaleInfoList)+"}";
    }

}
