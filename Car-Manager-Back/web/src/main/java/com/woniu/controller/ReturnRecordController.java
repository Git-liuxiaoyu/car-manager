package com.woniu.controller;


import com.woniu.domain.Car;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.service.CarService;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.ReturnRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/returnRecord")
public class ReturnRecordController {

    @Autowired
    private ReturnRecordService returnRecordService;

    @Autowired
    private CarService carService;

    @Autowired
    private DepartureRecordService departureRecordService;

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody ReturnRecord returnRecord){
        //求出总里程
        BigDecimal returnMileage=new BigDecimal(returnRecord.getReturnMileage());//回车里程
        BigDecimal mileage=new BigDecimal(returnRecord.getMileage());//出车里程
        BigDecimal zongMileage=returnMileage.add(mileage);
        returnRecord.setZongMileage(zongMileage);
        returnRecordService.add(returnRecord);//添加回车记录
        //出车成功修改车的状态
        Car car = carService.getById(returnRecord.getCarId());
        car.setCarStatus(38);
        //修改车的状态
        carService.update(car);
        DepartureRecord departureRecord=departureRecordService.getById(returnRecord.getId());
        departureRecord.setInTime(returnRecord.getInCarTime());
        //修改回车时间
        departureRecordService.update(departureRecord);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/getByDictionaryId")
    public ResponseResult getByDictionaryId(Integer id){
        ReturnRecord returnRecord=returnRecordService.getByDictionaryId(id);
        return new ResponseResult(returnRecord);
    }
}
