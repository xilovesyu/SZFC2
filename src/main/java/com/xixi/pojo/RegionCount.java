package com.xixi.pojo;

import javax.swing.plaf.synth.Region;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xijiaxiang on 2017/6/7.
 */
public class RegionCount {
    private Map<String,AreaCount> areas=new HashMap<>();

    public Map<String, AreaCount> getAreas() {
        return areas;
    }

    public void setAreas(Map<String, AreaCount> areas) {
        this.areas = areas;
    }

    public RegionCount() {
        Map<String,AreaCount> defaultareas=new HashMap<>();
        defaultareas.put("姑苏区",new AreaCount());
        defaultareas.put("高新区",new AreaCount());
        defaultareas.put("吴中区",new AreaCount());
        defaultareas.put("吴江区",new AreaCount());
        defaultareas.put("工业园区",new AreaCount());
        defaultareas.put("相城区",new AreaCount());
        for (String key : defaultareas.keySet()) {

            // System.out.println("Key = " + key);
            defaultareas.get(key).setZhu(0);
            defaultareas.get(key).setFeiZhu(0);
            defaultareas.get(key).setZhu_Area(0);
            defaultareas.get(key).setFeiZhu_Area(0);

        }
        this.areas = defaultareas;
    }

    @Override
    public String toString() {
        return "DayDeal{" +
                "areas=" + areas +
                '}';
    }

    public RegionCount add(RegionCount dayDeal){

        for (String key : dayDeal.areas.keySet()) {
            this.areas.get(key).setAreaName(dayDeal.areas.get(key).getAreaName());
            this.areas.get(key).setZhu(this.areas.get(key).getZhu()+dayDeal.areas.get(key).getZhu());
            this.areas.get(key).setFeiZhu(this.areas.get(key).getFeiZhu()+dayDeal.areas.get(key).getFeiZhu());
            this.areas.get(key).setZhu_Area(this.areas.get(key).getZhu_Area()+dayDeal.areas.get(key).getZhu_Area());
            this.areas.get(key).setFeiZhu_Area(this.areas.get(key).getFeiZhu_Area()+dayDeal.areas.get(key).getFeiZhu_Area());

        }
        return this;
    }
}
