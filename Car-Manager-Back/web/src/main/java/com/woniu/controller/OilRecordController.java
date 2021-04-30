package com.woniu.controller;


import com.woniu.dao.OilRecordDao;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.OilRecord;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.OilRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/OilRecord")
public class OilRecordController {

    @Autowired
    private OilRecordService oilRecordService;


    @RequestMapping("/list")
    public List<OilRecord> list(){

        List<OilRecord> oilRecords = oilRecordService.oilRecordList();
        return oilRecords;

    }

    @RequestMapping("/add")
    public ResponseResult add(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        OilRecord oilRecord = new OilRecord();
        oilRecord.setCarId(1);
        oilRecord.setOppositeCompanyId(1);
        oilRecord.setAddTime("2020-10-10 10:10:00");
        oilRecord.setOilType(2);
        oilRecord.setPrePrice(new BigDecimal("100.0"));
        oilRecord.setThisOilVolume(6.00);
        oilRecord.setLastOilVolume(60.00);

        oilRecord.setPrice(new BigDecimal("360"));
        oilRecord.setThisMileage(1000.00);
        oilRecord.setLastMileage(500.00);
        oilRecord.setRemarks("111");
        oilRecord.setDriverId(1);
        oilRecordService.add(oilRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("/update")
    public ResponseResult updated(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        OilRecord oilRecord = new OilRecord();
        oilRecord.setId(1);
        oilRecord.setOppositeCompanyId(1);
        oilRecord.setOilType(2);
        oilRecord.setPrePrice(new BigDecimal("100.0"));
        oilRecord.setThisOilVolume(6.00);
        oilRecord.setLastOilVolume(60.00);

        oilRecord.setPrice(new BigDecimal("360"));
        oilRecord.setThisMileage(1000.00);
        oilRecord.setLastMileage(500.00);
        oilRecord.setRemarks("9999999999");
        oilRecord.setDriverId(1);
        oilRecordService.update(oilRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("delete")
    public ResponseResult delete(){

        oilRecordService.delete(1);
        return ResponseResult.SUCCESS;
    }



}
