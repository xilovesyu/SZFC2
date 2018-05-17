package com.xixi.service;

import com.xixi.pojo.RegionCount;
import com.xixi.util.Excel;
import com.xixi.util.Property;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**下载的服务
 * Created by xijiaxiang on 2017/6/8.
 */
@Service
public class DownloadService {

    @Autowired
    private RegionCountService dealService;

    public ResponseEntity<byte[]> downnow(String fileLocatinKey, String filename) throws IOException {
        RegionCount dayDeal = null;
        File file = null;
        String filePath1 = Property.getProperty("fileLocation", "excelLocation");

        if (fileLocatinKey.equals("dealLocation")) {
            dayDeal = dealService.nowdaydeal();
            file = Excel.createExcel(filename, dayDeal,filePath1);
        }else if(fileLocatinKey.equals("canDealLocation")){
            dayDeal=dealService.cansaleDeal();
            file= Excel.createExcel2(filename,dayDeal,filePath1);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    public ResponseEntity<byte[]> downweek(String fileLocationKey, String filename, String startweek, String endweek) throws IOException {
        RegionCount dayDeal = dealService.monthdaydeal(fileLocationKey, startweek, endweek);
        File file =null;
        String filePath1 = Property.getProperty("fileLocation", "excelLocation");
        String filePath2 = Property.getProperty("fileLocation", "stockExcelLocation");
        switch(fileLocationKey){
            case "dealLocation":
                file=Excel.createExcel(filename,dayDeal,filePath1);
                break;
            case "canDealLocation":
                file=Excel.createExcel2(filename,dayDeal,filePath1);
                break;
            case "stockDealLocation":
                file=Excel.createExcel(filename,dayDeal,filePath2);
                break;
            case "canStockDealLocation":
                file=Excel.createExcel(filename,dayDeal,filePath2);
                break;
                default:
                    System.out.println("error download");
                    break;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
