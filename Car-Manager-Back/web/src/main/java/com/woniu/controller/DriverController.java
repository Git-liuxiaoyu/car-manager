package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Driver;
import com.woniu.domain.Employee;
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
    public ResponseResult<PageInfo<Driver>> list(Integer p, String searchText,Integer size){
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=driverService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<Driver> driverList = driverService.driverList(searchText,pageIndex,pageSize);
        PageInfo<Driver> pageInfo = new PageInfo<>(driverList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }
//    public List<Driver> list(){
//        List<Driver> drivers = driverService.driverList();
//        return drivers;
//    }

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
        //修改用户的角色为驾驶员（2）
        driverService.updateRole(driver.getEmployee().getId(),2);
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

    @RequestMapping("getAll")
    public ResponseResult<List<Driver>> getDriverName(){
        List<Driver> driverList = driverService.getAll();
        return  new ResponseResult<>(driverList);
    }


}
