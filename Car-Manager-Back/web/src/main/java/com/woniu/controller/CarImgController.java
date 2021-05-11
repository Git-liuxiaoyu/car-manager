package com.woniu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.woniu.domain.CarImg;
import com.woniu.service.CarImgService;
import com.woniu.service.CarService;
import com.woniu.util.ConstantPropertiesUtil;
import com.woniu.util.ResponseResult;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author Administrator
 * @Date 2021/5/10 17:53
 */
@RestController
@CrossOrigin
@RequestMapping("/carImg")
public class CarImgController {
    @Autowired
    private CarImgService carImgService;
    @RequestMapping("/upload")
    public ResponseResult add(MultipartFile file, CarImg carImg) throws IOException{
        // 地域节点
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        // BucketName
        String yourBucketName = ConstantPropertiesUtil.BUCKETNAME;

        try {
            // 1.获取上传文件 MultipartFile file
            // @RequestParam("file") file 与表单输入项的name值保持一致
            // 2.获取上传文件名称，获取上传文件输入流
            String fileName = file.getOriginalFilename();
            //
            String ext = fileName.substring(fileName.lastIndexOf("."));
            if(!(ext.equals(".jpg")|ext.equals(".png")|ext.equals(".gif")|ext.equals(".jpeg"))){
                return ResponseResult.FORBIDDEN;
            }
            // 在文件名称之前添加uuid值，保证文件名称不重复
            String uuid = UUID.randomUUID().toString();
            fileName = "CarImg/"+uuid + fileName;
            // 获取上传文件输入流
            InputStream in = file.getInputStream();
            // 3.把上传文件存储到阿里云oss里面
            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            // 第一个参数时BucketName，第二个参数是文件名称，第三个参数是输入流
            ossClient.putObject(yourBucketName, fileName, in);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 返回上传之后的oss存储路径
            String path = "http://" + yourBucketName + "." + endpoint + "/" + fileName;
            // 数据库存储
            carImg.setImgName(path);
            carImgService.add(carImg);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/getByCarId")
    public ResponseResult<List<CarImg>> show(Integer carId) throws IOException{
        List<CarImg> list = carImgService.queryByCarId(carId);
        return new ResponseResult<>(list);
    }
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        carImgService.delete(id);
        return ResponseResult.SUCCESS;
    }
}
