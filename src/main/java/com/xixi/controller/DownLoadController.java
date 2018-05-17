package com.xixi.controller;

import com.xixi.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**下载控制器
 * Created by xijiaxiang on 2017/6/8.
 */
@Controller
public class DownLoadController {

    @Autowired
    private DownloadService downloadService;


    /*@RequestMapping(value = "/dl/now")
    public ResponseEntity<byte[]> nowdeal(@RequestParam String filename) throws IOException {
        return downloadService.downnow(filename);
    }

    @RequestMapping(value = "/dl/week/{startweek:.+}-{endweek:.+}")
    public ResponseEntity<byte[]> weekdeal(@RequestParam String filename, @PathVariable String startweek, @PathVariable String endweek) throws IOException {
        return downloadService.downweek(filename,startweek,endweek);
    }*/

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> dl(@RequestParam String type,@RequestParam String startDate,@RequestParam String endDate,@RequestParam String filename) throws IOException {
//        if(type.equals("now")){
//           return  downloadService.downnow(fileLocationKey,filename);
//        }else{
//           String[] se= type.split("-");
//            return downloadService.downweek(fileLocationKey,filename,se[0],se[1]);
//        }
        return downloadService.downweek(type,filename,startDate,endDate);
       // filename="+startDate+","+endDate+".xls&startDate="+startDate+"&endDate="+endDate+"&type=sale
    }
}
