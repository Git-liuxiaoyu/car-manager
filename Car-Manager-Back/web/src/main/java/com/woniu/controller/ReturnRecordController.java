package com.woniu.controller;


import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.service.DepartureRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/returnRecord")
public class ReturnRecordController {

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
        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setInTime("");
        returnRecord.setReturnMileage(200.00);
        returnRecord.setStopPosition("123");
        returnRecord.setRemarks("123");
        return ResponseResult.SUCCESS;

    }





}
