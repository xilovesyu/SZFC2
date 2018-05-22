package com.xixi.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by xijiaxiang on 2018/5/12.
 */
public  class ViewStateInformation {
    public static Element getNetInformation(Document document,String key){
        Element viewStateE = document.getElementById(key);
        return viewStateE;
    }
}
