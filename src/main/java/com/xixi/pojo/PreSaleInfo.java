package com.xixi.pojo;

/**预售证的信息
 * Created by xijiaxiang on 2018/5/22.
 */
public class PreSaleInfo {
    public String yszNumber;
    public String projectName;
    public String projectAddress;

    public String yszStartDate;

    @Override
    public String toString() {
        return "PreSaleInfo{" +
                "yszNumber='" + yszNumber + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", yszStartDate='" + yszStartDate + '\'' +
                '}';
    }
}
