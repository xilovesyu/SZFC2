package com.xixi.pojo;

/**每个区域的成交量或者可售量的表示
 *
 * Created by xijiaxiang on 2017/6/7.
 */
public class AreaCount {
    private String areaName;
    private int Zhu;
    private int FeiZhu;
    private double Zhu_Area;
    private double FeiZhu_Area;

    public AreaCount() {
    }

    public AreaCount(String areaName, int zhu, int feiZhu, double zhu_Area, double feiZhu_Area) {
        this.areaName = areaName;
        Zhu = zhu;
        FeiZhu = feiZhu;
        Zhu_Area = zhu_Area;
        FeiZhu_Area = feiZhu_Area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getZhu() {
        return Zhu;
    }

    public void setZhu(int zhu) {
        Zhu = zhu;
    }

    public int getFeiZhu() {
        return FeiZhu;
    }

    public void setFeiZhu(int feiZhu) {
        FeiZhu = feiZhu;
    }

    public double getZhu_Area() {
        return Zhu_Area;
    }

    public void setZhu_Area(double zhu_Area) {
        Zhu_Area = zhu_Area;
    }

    public double getFeiZhu_Area() {
        return FeiZhu_Area;
    }

    public void setFeiZhu_Area(double feiZhu_Area) {
        FeiZhu_Area = feiZhu_Area;
    }

    @Override
    public String toString() {
        return "AreaCount{" +
                "areaName='" + areaName + '\'' +
                ", Zhu=" + Zhu +
                ", FeiZhu=" + FeiZhu +
                ", Zhu_Area=" + Zhu_Area +
                ", FeiZhu_Area=" + FeiZhu_Area +
                '}';
    }
}
