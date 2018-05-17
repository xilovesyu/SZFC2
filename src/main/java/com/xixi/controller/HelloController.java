package com.xixi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**c测试
 *
 * Created by xijiaxiang on 2017/11/10.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String toindex(){
        return "index";
    }
    @RequestMapping("/SalePage")
    public String toSale(){
        return "SalePage";
    }
    @RequestMapping("/CanSalePage")
    public String toCanSale(){
        return "CanSalePage";
    }
    @RequestMapping("/StockSalePage")
    public String toStockSale(){
        return "StockSalePage";
    }
    @RequestMapping("/StockCanSalePage")
    public String toStockCanSale(){
        return "StockCanSalePage";
    }
}
