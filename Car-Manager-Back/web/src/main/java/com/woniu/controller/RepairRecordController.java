package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Car;
import com.woniu.domain.RepairRecord;
import com.woniu.domain.RepairRecord;
import com.woniu.po.CarPo;
import com.woniu.service.CarService;
import com.woniu.service.RepairRecordService;
import com.woniu.service.RepairRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/repairRecord")
@CrossOrigin
public class RepairRecordController {

    @Autowired
    private RepairRecordService repairRecordService;

    //车辆
    @Autowired
    private CarService carService;

    @RequestMapping("/list")
    public ResponseResult<PageInfo<RepairRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=repairRecordService.count(searchText);
        List<RepairRecord> repairRecordList = repairRecordService.list(searchText,pageIndex,pageSize);
        PageInfo<RepairRecord> pageInfo = new PageInfo<>(repairRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody RepairRecord repairRecord){
        // 获取要维修车辆状态
        Car toRepairCar = carService.getById(repairRecord.getCarId());
        Integer carStatus = toRepairCar.getCarStatus();
        Integer status = toRepairCar.getStatus();
        if (carStatus!=38|status==0){
            return ResponseResult.FORBIDDEN;
        }
        repairRecordService.add(repairRecord);
        //修改车辆状态为维修
        Car car = new Car();
        car.setId(repairRecord.getCarId());
        car.setCarStatus(37);
        carService.update(car);

        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody RepairRecord repairRecord){
        repairRecordService.update(repairRecord);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/getCar")
    public ResponseResult getCar(@RequestBody RepairRecord repairRecord){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        repairRecord.setGetTime(sdf.format(now));
        //修改车辆状态为可用
        Car car = new Car();
        car.setId(repairRecord.getCarId());
        car.setCarStatus(38);
        carService.update(car);

        repairRecordService.update(repairRecord);
        return ResponseResult.SUCCESS;
    }


    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        repairRecordService.delete(id);
        return ResponseResult.SUCCESS;
    }

}
