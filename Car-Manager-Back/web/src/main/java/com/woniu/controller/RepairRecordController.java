package com.woniu.controller;


import com.woniu.domain.RepairRecord;
import com.woniu.domain.RepairRecord;
import com.woniu.service.RepairRecordService;
import com.woniu.service.RepairRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/repairRecord")
public class RepairRecordController {

    @Autowired
    private RepairRecordService repairRecordService;


    @RequestMapping("/list")
    public List<RepairRecord> list(){

        List<RepairRecord> repairRecordList = repairRecordService.findList();

        return repairRecordList;

    }

    @RequestMapping("/add")
    public ResponseResult add(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setDriverId(1);
        repairRecord.setCarId(1);
        repairRecord.setOppositeCompanyId(1);
        repairRecord.setSendTime("2021-4-5 12:00:00");
        repairRecord.setPreGetTime("2021-4-10 12:00:00");
        repairRecord.setReason("车灯损坏");
        repairRecordService.add(repairRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("/update")
    public ResponseResult updated(){
//        List<Role> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setDriverId(1);
        repairRecord.setOppositeCompanyId(1);
        repairRecord.setSendTime("2021-4-5 12:00:00");
        repairRecord.setPreGetTime("2021-4-10 12:00:00");
        repairRecord.setReason("车尾损坏");
        repairRecord.setRemarks("无");
        repairRecord.setId(1);
        repairRecordService.update(repairRecord);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("delete")
    public ResponseResult delete(){

        repairRecordService.delete(1);
        return ResponseResult.SUCCESS;
    }



}
