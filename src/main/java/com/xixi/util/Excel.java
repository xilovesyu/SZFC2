package com.xixi.util;

import com.xixi.pojo.AreaCount;
import com.xixi.pojo.RegionCount;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Map;

/**
 * Created by xijiaxiang on 2017/6/8.
 */
public class Excel {

    public static File createExcel(String fileName, RegionCount dayDeal,String ExcelLocation) throws IOException {
        Map<String, AreaCount> maps = dayDeal.getAreas();//每日成交量数据
        NumberFormat numberFormat = NumberFormat.getNumberInstance();//
        numberFormat.setMaximumFractionDigits(2);//保留两位小数
        numberFormat.setGroupingUsed(false);
        //
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("成交量");

        // 设置六列的宽度
        for (int i = 0; i < dayDeal.getAreas().size() + 2; i++) {
            sheet.setColumnWidth(i, 4000);
        }

        // 创建字体样式Verdana
        HSSFFont font =new FontAndStyle().new Font(wb,"Verdana").getFont();
        // 创建单元格样式24 47
        HSSFCellStyle styleblue =new FontAndStyle().new CellStyle(wb,24).getStyle();
        HSSFCellStyle styleyellow = new FontAndStyle().new CellStyle(wb,47).getStyle();

        HSSFCellStyle styledefault = wb.createCellStyle();
        styledefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styledefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //styledefault.setFillBackgroundColor(HSSFColor.WHITE.index);
        //styledefault.setFillForegroundColor(HSSFColor.WHITE.index);
        //styledefault.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置边框
        /*style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);*/

        styleblue.setFont(font);// 设置字体
        styleyellow.setFont(font);
        styledefault.setFont(font);

        // 创建标题一行，styleblue
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);// 设定行的高度
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(styleblue);
        cell.setCellValue("住宅");
        int count = 1;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styleblue);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaName());
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styleblue);
        cell.setCellValue("合计");

        //四行数据
        row = sheet.createRow(1);//套数
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("成交套数");
        count = 1;
        int counttaoshu = 0;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            int temp = dayDeal.getAreas().get(key).getZhu();
            cell.setCellValue(temp);
            counttaoshu += temp;
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        cell.setCellValue(counttaoshu);

        row = sheet.createRow(2);//面积
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("成交面积");
        count = 1;
        double countarea = 0.0;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            double temp = dayDeal.getAreas().get(key).getZhu_Area();
            cell.setCellValue(numberFormat.format(temp));
            countarea += temp;
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countarea));

        row = sheet.createRow(3);//均套面积
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("均套面积");
        count = 1;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            if (dayDeal.getAreas().get(key).getZhu() == 0) {
                cell.setCellValue(0.00);
            } else {
                cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getZhu_Area() / (double) dayDeal.getAreas().get(key).getZhu()));
            }
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        if (counttaoshu != 0) {
            cell.setCellValue(numberFormat.format(countarea / (double) counttaoshu));
        } else {
            cell.setCellValue(0.00);
        }


        //下面非住宅
        row = sheet.createRow(6);
        row.setHeight((short) 500);// 设定行的高度
        //row.setRowStyle(styleblue);
        cell = row.createCell(0);
        cell.setCellStyle(styleblue);
        cell.setCellValue("非住宅");
        count = 1;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styleblue);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaName());
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styleblue);
        cell.setCellValue("合计");

        //四行数据
        row = sheet.createRow(7);//套数
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("成交套数");
        count = 1;
        counttaoshu = 0;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            counttaoshu += dayDeal.getAreas().get(key).getFeiZhu();
            cell.setCellValue(dayDeal.getAreas().get(key).getFeiZhu());
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        cell.setCellValue(counttaoshu);


        row = sheet.createRow(8);//面积
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("成交面积");
        count = 1;
        countarea = 0;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            countarea += dayDeal.getAreas().get(key).getFeiZhu_Area();
            cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getFeiZhu_Area()));
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countarea));

        row = sheet.createRow(9);//均套面积
        row.setHeight((short) 500);// 设定行的高度

        cell = row.createCell(0);
        cell.setCellStyle(styleyellow);
        cell.setCellValue("均套面积");
        count = 1;
        for (String key : dayDeal.getAreas().keySet()) {
            cell = row.createCell(count);
            cell.setCellStyle(styledefault);
            if (dayDeal.getAreas().get(key).getFeiZhu() == 0) {
                cell.setCellValue(0.00);
            } else {
                cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getFeiZhu_Area() / (double) dayDeal.getAreas().get(key).getFeiZhu()));
            }
            count++;
        }
        cell = row.createCell(count);
        cell.setCellStyle(styledefault);
        if(counttaoshu!=0) {
            cell.setCellValue(numberFormat.format(countarea / (counttaoshu)));
        }
        else
        {
            cell.setCellValue(0.00);
        }

       // String filePath = Property.getProperty("fileLocation", "excelLocation");
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

    public static File createExcel2(String fileName,RegionCount dayDeal,String ExcelLocation) throws IOException {
        Map<String, AreaCount> maps = dayDeal.getAreas();//每日成交量数据
        NumberFormat numberFormat = NumberFormat.getNumberInstance();//
        numberFormat.setMaximumFractionDigits(2);//保留两位小数
        numberFormat.setGroupingUsed(false);
        //
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("可售房源");

        // 设置六列的宽度
        for (int i = 0; i <=5; i++) {
            if(i==5){
                sheet.setColumnWidth(i, 12000);
            }else{
            sheet.setColumnWidth(i, 4000);}
        }

        // 创建字体样式
        HSSFFont font = new FontAndStyle().new Font(wb,"Verdana").getFont();

        // 创建单元格样式053
        HSSFCellStyle styleorange = new FontAndStyle().new CellStyle(wb,53).getStyle();

        HSSFCellStyle styledefault = wb.createCellStyle();
        styledefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styledefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        styleorange.setFont(font);// 设置字体
        styledefault.setFont(font);

        // 创建标题一行，styleblue
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);// 设定行的高度
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(styleorange);
        cell.setCellValue("可售房源信息公示");
       // 合并单元格
        CellRangeAddress cellRangeAddress =new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);


        //七行数据
        int count=1;
        int countzongzhu=0;int countzong=0;
        double countzongzhuarea=0;double countzongarea=0;
        for (String key : dayDeal.getAreas().keySet()) {
            row = sheet.createRow(count);
            cell = row.createCell(0);
            cell.setCellStyle(styledefault);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaName());
            cell=row.createCell(1);
            cell.setCellStyle(styledefault);
            cell.setCellValue("小计");
            cell=row.createCell(2);
            cell.setCellStyle(styledefault);
            cell.setCellValue("套数");
            cell=row.createCell(3);
            cell.setCellStyle(styledefault);
            cell.setCellValue((dayDeal.getAreas().get(key).getZhu()+dayDeal.getAreas().get(key).getFeiZhu())+"套");
            countzong+=dayDeal.getAreas().get(key).getZhu()+dayDeal.getAreas().get(key).getFeiZhu();
            cell=row.createCell(4);
            cell.setCellStyle(styledefault);
            cell.setCellValue("总建筑面积");
            cell=row.createCell(5);
            cell.setCellStyle(styledefault);
            cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getZhu_Area()+dayDeal.getAreas().get(key).getFeiZhu_Area())+"平方米");
            countzongarea+=dayDeal.getAreas().get(key).getZhu_Area()+dayDeal.getAreas().get(key).getFeiZhu_Area();
            count++;
            row=sheet.createRow(count);
            cell = row.createCell(0);
            /*cell.setCellStyle(styledefault);
            cell.setCellValue(dayDeal.getAreas().get(key).getAreaname());*/
            cell=row.createCell(1);
            cell.setCellStyle(styledefault);
            cell.setCellValue("其中住宅");
            cell=row.createCell(2);
            cell.setCellStyle(styledefault);
            cell.setCellValue("套数");
            cell=row.createCell(3);
            cell.setCellStyle(styledefault);
            cell.setCellValue((dayDeal.getAreas().get(key).getZhu())+"套");
            countzongzhu+=dayDeal.getAreas().get(key).getZhu();
            cell=row.createCell(4);
            cell.setCellStyle(styledefault);
            cell.setCellValue("总建筑面积");
            cell=row.createCell(5);
            cell.setCellStyle(styledefault);
            cell.setCellValue(numberFormat.format(dayDeal.getAreas().get(key).getZhu_Area())+"平方米");
            countzongzhuarea+=dayDeal.getAreas().get(key).getZhu_Area();
            count++;
        }
        row=sheet.createRow(count);
        cell = row.createCell(0);
        cell.setCellStyle(styledefault);
        cell.setCellValue("市区");
        cell=row.createCell(1);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总计");
        cell=row.createCell(2);
        cell.setCellStyle(styledefault);
        cell.setCellValue("套数");
        cell=row.createCell(3);
        cell.setCellStyle(styledefault);
        cell.setCellValue(countzong+"套");
        cell=row.createCell(4);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总建筑面积");
        cell=row.createCell(5);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countzongarea)+"平方米");
        count++;
        row=sheet.createRow(count);
        cell = row.createCell(0);
        cell.setCellStyle(styledefault);
        //cell.setCellValue("市区");
        cell=row.createCell(1);
        cell.setCellStyle(styledefault);
        cell.setCellValue("其中住宅");
        cell=row.createCell(2);
        cell.setCellStyle(styledefault);
        cell.setCellValue("套数");
        cell=row.createCell(3);
        cell.setCellStyle(styledefault);
        cell.setCellValue(countzongzhu+"套");
        cell=row.createCell(4);
        cell.setCellStyle(styledefault);
        cell.setCellValue("总建筑面积");
        cell=row.createCell(5);
        cell.setCellStyle(styledefault);
        cell.setCellValue(numberFormat.format(countzongzhuarea)+"平方米");


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
