package com.woniu.controller;


import com.woniu.domain.Driver;
import com.woniu.po.DriverPo;
import com.woniu.po.EmployeePo;
import com.woniu.service.DriverService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/driver")
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
    public ResponseResult add(@RequestBody Driver driver){
//        List<RolePo> roles = roleService.roles();
//        List<Driver> drivers = driverService.driverList();
//        Driver driver = new Driver();
//        driver.setDriverNum("123");
//        driver.setStatus(1);
//        driver.setType(1);
//        driver.setEmployeeId(1);
        driver.setEmployeeId(driver.getEmployee().getId());
        driverService.updateRole(driver.getEmployee().getId());
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

    @RequestMapping("dname")
    public ResponseResult<EmployeePo> getName(String name){

        EmployeePo employee = driverService.getEmployee(name);
        if (employee!=null){
            return  new ResponseResult(employee);
        }
        return new ResponseResult(101,"公司没有此员工请联系管理员添加");
    }




}
