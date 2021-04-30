package com.woniu.controller;


import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Driver;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.DriverService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departureRecord")
public class DepartureRecordController {

    @Autowired
    private DepartureRecordService departureRecordService;


    @RequestMapping("/list")
    public List<DepartureRecord> list(){
//        List<Role> roles = roleService.roles();
        List<DepartureRecord> departureRecords = departureRecordService.departureRecordList();
//        List<DriverPo> driverPos = driverService.driverList();
        return departureRecords;

    }

    @RequestMapping("/add")
    public ResponseResult add(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();

        DepartureRecord departureRecord = new DepartureRecord();
        departureRecord.setDeptId(1);
        departureRecord.setDestination("武汉");
        departureRecord.setCarId(1);
        departureRecord.setEmployeeId(1);
        departureRecord.setFollowPerson("李四");
        departureRecord.setOutTime("2021-1-1 18:0:0");
        departureRecord.setPreInTime("2021-2-1 18:0:0");
        departureRecord.setMileage(100.00);
        departureRecord.setDriverId(1);
        departureRecordService.add(departureRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("/update")
    public ResponseResult updated(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        DepartureRecord departureRecord = new DepartureRecord();
        departureRecord.setId(2);
        departureRecord.setDeptId(1);
        departureRecord.setDestination("北京");
        departureRecord.setCarId(1);
        departureRecord.setEmployeeId(1);
        departureRecord.setFollowPerson("李四");
        departureRecord.setOutTime("2021-1-1 18:0:0");
        departureRecord.setPreInTime("2021-2-1 18:0:0");
        departureRecord.setMileage(100.00);
        departureRecord.setDriverId(1);
        departureRecordService.update(departureRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("delete")
    public ResponseResult delete(){

        departureRecordService.delete(2);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("getById")
    public DepartureRecord getById(){


        return departureRecordService.getById(1);
    }



}
