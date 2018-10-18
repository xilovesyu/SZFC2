package com.xixi.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Created by xijiaxiang on 2017/6/10.
 */
public class FontAndStyle {
     class CellStyle{
         HSSFCellStyle style;
         public CellStyle(HSSFWorkbook wb,int colorNum){
              style = wb.createCellStyle();
             // styleblue.setFillBackgroundColor(HSSFColor.BLUE.index);
             //style.setFillForegroundColor(HSSFColor.BLUE.index);
             style.setFillForegroundColor((short)colorNum);
             //style.setFillBackgroundColor((short) colorNum);
             style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        }

         public HSSFCellStyle getStyle() {
             return style;
         }
     }
    class Font{
        HSSFFont font;
         public Font(HSSFWorkbook wb,String fontName){
             font= wb.createFont();
             font.setFontName(fontName);
             font.setBoldweight((short) 100);
             font.setFontHeight((short) 300);
             font.setColor(HSSFColor.BLACK.index);
         }

        public HSSFFont getFont() {
            return font;
        }
    }

}
