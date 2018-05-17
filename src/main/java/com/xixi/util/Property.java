package com.xixi.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by xijiaxiang on 2017/6/7.
 */
public class Property {
    public static Properties pro = new Properties();
    public static String getProperty(String location,String key){
        //Properties pro = new Properties();
        String value="";
        try {
            pro.load(Property.class.getClassLoader().getResourceAsStream(location+".properties"));
            value= pro.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
