package com.woniu.controller;


import com.woniu.domain.Driver;
import com.woniu.po.DriverPo;
import com.woniu.service.DriverService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;


    @RequestMapping("/list")
    public List<Driver> list(){
//        List<RolePo> roles = roleService.roles();
        List<Driver> drivers = driverService.driverList();
//        List<DriverPo> driverPos = driverService.driverList();
        return drivers;

    }

    @RequestMapping("/addDriver")
    public ResponseResult add(){
//        List<RolePo> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        Driver driver = new Driver();
        driver.setDriverNum("123");
        driver.setStatus(1);
        driver.setType(1);
        driver.setEmployeeId(1);
        driverService.addDriver(driver);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("/updated")
    public ResponseResult updated(){
//        List<RolePo> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
        Driver driver = new Driver();
        driver.setId(1);
        driver.setDriverNum("999");
        driver.setStatus(1);
        driver.setType(1);
        driver.setEmployeeId(1);
        driverService.updateDriver(driver);
        return ResponseResult.SUCCESS;

    }

    @RequestMapping("del")
    public ResponseResult del(){

        driverService.delDriver(4);
        return ResponseResult.SUCCESS;
    }



}
