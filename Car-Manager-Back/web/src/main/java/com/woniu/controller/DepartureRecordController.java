package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Driver;
import com.woniu.domain.Employee;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.DriverService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/departureRecord")
public class DepartureRecordController {

    @Autowired
    private DepartureRecordService departureRecordService;

    //分页查询
    @RequestMapping("/list")
    public ResponseResult<List<DepartureRecord>> departureRecordList(Integer p, String searchText, Integer size) {
        int pageIndex = 1;
        int pageSize = 5;
        if (p >= 1) {
            pageIndex = p;
        }
        if (pageSize >= 5) {
            pageSize = size;
        }
        Integer total = departureRecordService.count(searchText);
        PageHelper.startPage(pageIndex, pageSize);
        List<DepartureRecord> departureRecordList = departureRecordService.findAll(searchText, pageIndex, pageSize);
        PageInfo<DepartureRecord> pageInfo = new PageInfo<>(departureRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add() {
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
    public ResponseResult updated() {
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
    public ResponseResult delete() {

        departureRecordService.delete(2);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("getById")
    public DepartureRecord getById() {


        return departureRecordService.getById(1);
    }


}
