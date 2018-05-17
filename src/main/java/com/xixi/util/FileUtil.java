package com.xixi.util;

import com.alibaba.fastjson.JSON;
import com.xixi.pojo.RegionCount;

import java.io.*;
import java.time.Instant;

/**写入对象
 * Created by xijiaxiang on 2017/11/10.
 */
public class FileUtil {

    String key;
    public FileUtil(String key){
        this.key=key;
    }
    public void writeDeal(RegionCount regionCount){
        File file = new File( Property.getProperty("fileLocation",this.key)+File.separator+ MyTimer.getShangHaiTime() + ".txt");
        //System.out.println(file.getAbsolutePath());
        if(file.exists()){
            file.delete();
            try {
                file.createNewFile();
                file.setLastModified(Instant.now().toEpochMilli());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(file),"utf-8"));

            writer.write(JSON.toJSONString(regionCount));
            writer.newLine();
            writer.close();
        } catch (Exception e) {

        }
    }
}
