package com.xixi.util;

import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Map;

/**
 * Created by xijiaxiang on 2017/6/8.
 * modify by xijiaxiang 2018/08/27
 */
public class Excel {
     /* HSSFCellStyle styleyellow = new FontAndStyle().new CellStyle(wb,47).getStyle();

        HSSFCellStyle styledefault = wb.createCellStyle();
        styledefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styledefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
    //styledefault.setFillBackgroundColor(HSSFColor.WHITE.index);
    //styledefault.setFillForegroundColor(HSSFColor.WHITE.index);
    //styledefault.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

    // 设置边框
        /*style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);*/

    /* styleblue.setFont(font);// 设置字体
     styleyellow.setFont(font);
     styledefault.setFont(font);*/

    private static HSSFFont getCustomFont(HSSFWorkbook wb, String fontName, boolean isBold, short fontSize, RGB fontColor,short colorIndex) {
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(colorIndex,(byte) fontColor.getR(), (byte) fontColor.getG(), (byte) fontColor.getB());

        HSSFFont font = new FontAndStyle().new Font(wb, fontName).getFont();
        font.setBold(isBold);
        font.setFontHeightInPoints(fontSize);
        font.setColor(colorIndex);
        return font;
    }

    private static HSSFCellStyle getCustomCellStyle(HSSFWorkbook wb, HSSFFont font, HorizontalAlignment horAlign, VerticalAlignment verAlign, RGB color,short colorIndex) {
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(colorIndex,(byte) color.getR(), (byte) color.getG(), (byte) color.getB());

        HSSFCellStyle cellStyle = new FontAndStyle().new CellStyle(wb, colorIndex).getStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(horAlign);
        cellStyle.setVerticalAlignment(verAlign);

        return cellStyle;
    }
    private static HSSFCellStyle getCustomCellStyle(HSSFWorkbook wb, HSSFFont font, HorizontalAlignment horAlign, VerticalAlignment verAlign, RGB color,short colorIndex, BorderStyle borderStyle,RGB borderColor,short borderColorIndex) {
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(colorIndex,(byte) color.getR(), (byte) color.getG(), (byte) color.getB());
        customPalette.setColorAtIndex(borderColorIndex,(byte) borderColor.getR(), (byte) borderColor.getG(), (byte) borderColor.getB());

        HSSFCellStyle cellStyle = new FontAndStyle().new CellStyle(wb, colorIndex).getStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(horAlign);
        cellStyle.setVerticalAlignment(verAlign);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBottomBorderColor(borderColorIndex);
        cellStyle.setTopBorderColor(borderColorIndex);
        cellStyle.setLeftBorderColor(borderColorIndex);
        cellStyle.setRightBorderColor(borderColorIndex);
        return cellStyle;
    }
    private static void createCustomCell(HSSFRow row, HSSFCellStyle style, String cellValue, int columnIndex) {
        HSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(style);
        cell.setCellValue(cellValue);
    }
    private static HSSFCellStyle setFirstRowCellStyle(HSSFWorkbook wb){
        // 创建字体样式微软雅黑
        HSSFFont font = getCustomFont(wb, "微软雅黑", true, (short) 14, new RGB(0,0,0),(short)8);
        // 创建单元格样式
        HSSFCellStyle style = getCustomCellStyle(wb, font, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, new RGB(255,255,255),(short)9);
        return style;
    }

    private static HSSFCellStyle setSecondRowCellStyle(HSSFWorkbook wb){
        HSSFFont font = getCustomFont(wb, "微软雅黑", true, (short) 14, new RGB(255,255,255),(short)9);

        HSSFCellStyle style = getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,new RGB(0,128,128),(short)30,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static HSSFCellStyle setSecondRowCellStyle(HSSFWorkbook wb,RGB backColor,int colorIndex){
        HSSFFont font = getCustomFont(wb, "微软雅黑", true, (short) 14, new RGB(255,255,255),(short)9);

        HSSFCellStyle style = getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,backColor,(short)colorIndex,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static HSSFCellStyle setThirdRowFirstColumnCellStyle(HSSFWorkbook wb){
        HSSFFont font=getCustomFont(wb,"微软雅黑",true,(short)12,new RGB(255,255,255),(short)9);
        HSSFCellStyle style= getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,new RGB(0,128,128),(short)30,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static HSSFCellStyle setThirdRowFirstColumnCellStyle(HSSFWorkbook wb,RGB backColor,int colorIndex){
        HSSFFont font=getCustomFont(wb,"微软雅黑",true,(short)12,new RGB(255,255,255),(short)9);
        HSSFCellStyle style= getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,backColor,(short)colorIndex,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static HSSFCellStyle setMainCellStyle(HSSFWorkbook wb){
        HSSFFont font=getCustomFont(wb,"微软雅黑",false,(short)14,new RGB(0,0,0),(short)8);
        HSSFCellStyle style= getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,new RGB(255,255,255),(short)9,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }

    private static HSSFCellStyle setAllCountCellStyle(HSSFWorkbook wb){
        HSSFFont font=getCustomFont(wb,"微软雅黑",true,(short)14,new RGB(112,48,160),(short)15);
        HSSFCellStyle style= getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,new RGB(255,255,255),(short)9,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static HSSFCellStyle setAllCountCellStyle(HSSFWorkbook wb,RGB rgb,int fontColorIndex){
        HSSFFont font=getCustomFont(wb,"微软雅黑",true,(short)14,rgb,(short)fontColorIndex);
        HSSFCellStyle style= getCustomCellStyle(wb,font,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,new RGB(255,255,255),(short)9,
                BorderStyle.MEDIUM,new RGB(166,166,166),(short)22);
        return style;
    }
    private static int getHouseAccount(boolean isZhu,Map<String,AreaCount> maps,String key){
        if(isZhu){
            return maps.get(key).getZhu();
        }else{
            return maps.get(key).getFeiZhu();
        }
    }
    private static double getHouseArea(boolean isZhu,Map<String,AreaCount> maps,String key){
        if(isZhu){
            return maps.get(key).getZhu_Area();
        }else{
            return maps.get(key).getFeiZhu_Area();
        }
    }

    private static double[] createNewHouseDealTable2(HSSFWorkbook wb,RegionCount dayDeal,HSSFSheet sheet,boolean isNewHouse,boolean isZhu,RGB backColor1,int backColorIndex,RGB fontColor2,int fontColorIndex){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();//
        numberFormat.setMaximumFractionDigits(2);//保留两位小数
        numberFormat.setGroupingUsed(false);
        Map<String, AreaCount> maps = dayDeal.getAreas();//每日成交量数据
        int rowNum=isZhu?0:7;
        // 设置六列的宽度
        for (int i = 0; i < dayDeal.getAreas().size() + 2; i++) {
            sheet.setColumnWidth(i, 4000);
        }

        // 创建标题一行
        HSSFCellStyle style= setFirstRowCellStyle(wb);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeight((short) 420);// 设定行的高度 excel行高*20
        String title="";
        if(isNewHouse){
            title+="（新房）";
            if(isZhu){
                title+="住宅昨日各区签约数据";
            }else{
                title+="非住宅昨日各区签约数据";
            }
        }else{
            title+="（二手房）";
            if(isZhu){
                title+="住宅昨日各区签约数据";
            }else{
                title+="非住宅昨日各区签约数据";
            }
        }
        createCustomCell(row, style, title, 0);

        //创建四行
        //第一行column attribute
        style= setSecondRowCellStyle(wb,backColor1,backColorIndex);
        row = sheet.createRow(rowNum+1);
        createCustomCell(row, style, "区域", 0);
        createCustomCell(row,style,"姑苏区",1);
        createCustomCell(row,style,"吴中区",2);
        createCustomCell(row,style,"相城区",3);
        createCustomCell(row,style,"园区",4);
        createCustomCell(row,style,"新区",5);
        createCustomCell(row,style,"吴江区",6);
        createCustomCell(row, style, "合计", 7);
        createCustomCell(row, style, "总计", 8);

        //四行数据//1
        row = sheet.createRow(rowNum+2);
        row.setHeight((short) 720);// 36*20
        style=setThirdRowFirstColumnCellStyle(wb,backColor1,backColorIndex);
        createCustomCell(row,style,"成交套数\n（套）",0);

        style=setMainCellStyle(wb);
        int allZhu=0;
       // boolean isZhu=false;
        int tempZhu=getHouseAccount(isZhu,maps,"姑苏区"); allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",1);
        tempZhu=getHouseAccount(isZhu,maps,"吴中区");allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",2);
        tempZhu=getHouseAccount(isZhu,maps,"相城区");allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",3);
        tempZhu=getHouseAccount(isZhu,maps,"工业园区");allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",4);
        tempZhu=getHouseAccount(isZhu,maps,"高新区");allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",5);
        tempZhu=getHouseAccount(isZhu,maps,"吴江区");allZhu+=tempZhu;
        createCustomCell(row,style,tempZhu+"",6);
        style=setAllCountCellStyle(wb,fontColor2,fontColorIndex);
        createCustomCell(row,style,allZhu+"",7);

        createCustomCell(row,style,"",8);

        //2
        row = sheet.createRow(rowNum+3);
        row.setHeight((short) 720);// 36*20
        style=setThirdRowFirstColumnCellStyle(wb,backColor1,backColorIndex);
        createCustomCell(row,style,"成交面积\n（㎡）",0);

        style=setMainCellStyle(wb);
        double allZhuArea=0;
        double tempZhuArea=getHouseArea(isZhu,maps,"姑苏区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",1);
        tempZhuArea=getHouseArea(isZhu,maps,"吴中区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",2);
        tempZhuArea=getHouseArea(isZhu,maps,"相城区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",3);
        tempZhuArea=getHouseArea(isZhu,maps,"工业园区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",4);
        tempZhuArea=getHouseArea(isZhu,maps,"高新区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",5);
        tempZhuArea=getHouseArea(isZhu,maps,"吴江区"); allZhuArea+=tempZhuArea;
        createCustomCell(row,style,numberFormat.format(tempZhuArea)+"",6);

        style=setAllCountCellStyle(wb,fontColor2,fontColorIndex);
        createCustomCell(row,style,numberFormat.format(allZhuArea)+"",7);

        createCustomCell(row,style,"",8);

        //3
        row = sheet.createRow(rowNum+4);
        row.setHeight((short) 720);// 36*20
        style=setThirdRowFirstColumnCellStyle(wb,backColor1,backColorIndex);
        createCustomCell(row,style,"均套面积\n（㎡）",0);

        style=setMainCellStyle(wb);
        double averageArea=0;
        Map<String,AreaCount> areaCount=dayDeal.getAreas();
        double tempAverArea=getHouseAccount(isZhu,maps,"姑苏区")==0?0:getHouseArea(isZhu,maps,"姑苏区")/(double)getHouseAccount(isZhu,maps,"姑苏区");
        averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",1);
        tempAverArea=getHouseAccount(isZhu,maps,"吴中区")==0?0:getHouseArea(isZhu,maps,"吴中区")/(double)getHouseAccount(isZhu,maps,"吴中区"); averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",2);
        tempAverArea=getHouseAccount(isZhu,maps,"相城区")==0?0:getHouseArea(isZhu,maps,"相城区")/(double)getHouseAccount(isZhu,maps,"相城区"); averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",3);
        tempAverArea=getHouseAccount(isZhu,maps,"工业园区")==0?0:getHouseArea(isZhu,maps,"工业园区")/(double)getHouseAccount(isZhu,maps,"工业园区"); averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",4);
        tempAverArea=getHouseAccount(isZhu,maps,"高新区")==0?0:getHouseArea(isZhu,maps,"高新区")/(double)getHouseAccount(isZhu,maps,"高新区"); averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",5);
        tempAverArea=getHouseAccount(isZhu,maps,"吴江区")==0?0:getHouseArea(isZhu,maps,"吴江区")/(double)getHouseAccount(isZhu,maps,"吴江区"); averageArea+=tempAverArea;
        createCustomCell(row,style,numberFormat.format(tempAverArea)+"",6);
        style=setAllCountCellStyle(wb,fontColor2,fontColorIndex);
        createCustomCell(row,style,numberFormat.format(allZhuArea/(double)allZhu)+"",7);

        createCustomCell(row,style,"",8);

        return new double[]{allZhu,allZhuArea};
    }
   /* public static File createExcel(String fileName, RegionCount dayDeal, String ExcelLocation) throws IOException {
        //
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("新房成交量统计");
        double result[]=createNewHouseDealTable2(wb,dayDeal,sheet,true,new RGB(0,128,128),30,new RGB(112,48,160),(short)15);
        double result1[]=createNewHouseDealTable2(wb,dayDeal,sheet,false,new RGB(198,89,17),19,new RGB(47,117,181),20);

        //总计
        HSSFRow row=sheet.createRow(14);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("总计："+(result[0]+result1[0]));
        cell=row.createCell(1);
        cell.setCellValue("总计面积："+(result[1]+result1[1]));

        File file = new File(ExcelLocation + fileName);
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(file);
        wb.write(os);
        os.close();

        return file;
    }*/
    public static File createExcel(String fileName, RegionCount dayDeal, String ExcelLocation,boolean isNewHouse) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("二手房房成交量统计");
        double result[]=createNewHouseDealTable2(wb,dayDeal,sheet,isNewHouse,true,new RGB(0,128,128),30,new RGB(112,48,160),(short)15);
        double result1[]=createNewHouseDealTable2(wb,dayDeal,sheet,isNewHouse,false,new RGB(198,89,17),19,new RGB(47,117,181),20);

        //总计
        HSSFRow row=sheet.createRow(14);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("总计：");
        cell=row.createCell(1);
        cell.setCellValue(result[0]+result1[0]);
        cell=row.createCell(2);
        cell.setCellValue("总计面积：");
        cell=row.createCell(3);
        cell.setCellValue(result[1]+result1[1]);

        File file = new File(ExcelLocation + fileName);
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(file);
        wb.write(os);
        os.close();

        return file;
    }

    public static File createExcel2(String fileName, RegionCount dayDeal, String ExcelLocation) throws IOException {
        Map<String, AreaCount> maps = dayDeal.getAreas();//每日成交量数据
        NumberFormat numberFormat = NumberFormat.getNumberInstance();//
        numberFormat.setMaximumFractionDigits(2);//保留两位小数
        numberFormat.setGroupingUsed(false);
        //
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("可售房源");

        // 设置六列的宽度
        for (int i = 0; i <= 5; i++) {
            if (i == 5) {
                sheet.setColumnWidth(i, 12000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        // 创建字体样式
        HSSFFont font = new FontAndStyle().new Font(wb, "Verdana").getFont();

        // 创建单元格样式053
        HSSFCellStyle styleorange = new FontAndStyle().new CellStyle(wb, 53).getStyle();

        HSSFCellStyle styledefault = wb.createCellStyle();
        //styledefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //styledefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        styleorange.setFont(font);// 设置字体
        styledefault.setFont(font);

        // 创建标题一行，styleblue
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);// 设定行的高度
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(styleorange);
        cell.setCellValue("可售房源信息公示");
        // 合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);


        //七行数据
        int count = 1;
        int countzongzhu = 0;
        int countzong = 0;
        double countzongzhuarea = 0;
        double countzongarea = 0;
        for (String key : dayDeal.getAreas().keySet()) {
            row = sheet.createRow(count);
            cell = row.createCell(0);
            cell.setCellStyle(styledefault);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaName());
            cell = row.createCell(1);
            cell.setCellStyle(styledefault);
            cell.setCellValue("小计");
            cell = row.createCell(2);
            cell.setCellStyle(styledefault);
            cell.setCellValue("套数");
            cell = row.createCell(3);
            cell.setCellStyle(styledefault);
            cell.setCellValue((dayDeal.getAreas().get(key).getZhu() + dayDeal.getAreas().get(key).getFeiZhu()) + "套");
            countzong += dayDeal.getAreas().get(key).getZhu() + dayDeal.getAreas().get(key).getFeiZhu();
            cell = row.createCell(4);
            cell.setCellStyle(styledefault);
            cell.setCellValue("总建筑面积");
            cell = row.createCell(5);
            cell.setCellStyle(styledefault);
            cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getZhu_Area() + dayDeal.getAreas().get(key).getFeiZhu_Area()) + "平方米");
            countzongarea += dayDeal.getAreas().get(key).getZhu_Area() + dayDeal.getAreas().get(key).getFeiZhu_Area();
            count++;
            row = sheet.createRow(count);
            cell = row.createCell(0);
            /*cell.setCellStyle(styledefault);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaname());*/
            cell = row.createCell(1);
            cell.setCellStyle(styledefault);
            cell.setCellValue("其中住宅");
            cell = row.createCell(2);
            cell.setCellStyle(styledefault);
            cell.setCellValue("套数");
            cell = row.createCell(3);
            cell.setCellStyle(styledefault);
            cell.setCellValue((dayDeal.getAreas().get(key).getZhu()) + "套");
            countzongzhu += dayDeal.getAreas().get(key).getZhu();
            cell = row.createCell(4);
            cell.setCellStyle(styledefault);
            cell.setCellValue("总建筑面积");
            cell = row.createCell(5);
            cell.setCellStyle(styledefault);
            cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getZhu_Area()) + "平方米");
            countzongzhuarea += dayDeal.getAreas().get(key).getZhu_Area();
            count++;
        }
        row = sheet.createRow(count);
        cell = row.createCell(0);
        cell.setCellStyle(styledefault);
        cell.setCellValue("市区");
        cell = row.createCell(1);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总计");
        cell = row.createCell(2);
        cell.setCellStyle(styledefault);
        cell.setCellValue("套数");
        cell = row.createCell(3);
        cell.setCellStyle(styledefault);
        cell.setCellValue(countzong + "套");
        cell = row.createCell(4);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总建筑面积");
        cell = row.createCell(5);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countzongarea) + "平方米");
        count++;
        row = sheet.createRow(count);
        cell = row.createCell(0);
        cell.setCellStyle(styledefault);
        //cell.setCellValue("市区");
        cell = row.createCell(1);
        cell.setCellStyle(styledefault);
        cell.setCellValue("其中住宅");
        cell = row.createCell(2);
        cell.setCellStyle(styledefault);
        cell.setCellValue("套数");
        cell = row.createCell(3);
        cell.setCellStyle(styledefault);
        cell.setCellValue(countzongzhu + "套");
        cell = row.createCell(4);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总建筑面积");
        cell = row.createCell(5);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countzongzhuarea) + "平方米");


        //String filePath = Property.getProperty("fileLocation", "excelLocation");
        File file = new File(ExcelLocation + fileName);
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(file);
        wb.write(os);
        os.close();

        return file;
    }
}
class RGB{
    private int r,g,b;
    public RGB(int r,int g, int b){
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}