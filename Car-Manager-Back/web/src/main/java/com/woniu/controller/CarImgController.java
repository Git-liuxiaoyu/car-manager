package com.woniu.controller;

import com.woniu.domain.CarImg;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
/**
 * @Author Administrator
 * @Date 2021/5/10 17:53
 */
@RestController
@CrossOrigin
@RequestMapping("/carImg")
public class CarImgController {
    @RequestMapping("/upload")
    public void save(MultipartFile file,CarImg carImg) throws IOException{
        System.out.println(carImg);
        File f = new File("D:/img");
        if(!f.exists()){
            f.mkdirs();
        }
        File targetFile = new File(f,carImg.getImgName());
        try {
            //完成上传操作
            file.transferTo(targetFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
