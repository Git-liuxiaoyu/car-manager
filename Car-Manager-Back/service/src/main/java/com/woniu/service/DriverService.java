package com.woniu.service;


import com.woniu.domain.Driver;
import com.woniu.po.EmployeePo;

import java.util.List;

public interface DriverService {

    public List<Driver> driverList();

    int addDriver(Driver driver);

    int updateDriver(Driver driver);

    int delDriver(Integer id);

    int updateRole(Integer employeeId);
    //获取驾驶员的中员工列表的信息
    EmployeePo getEmployee(String name);


    //获取驾驶员的姓名
    List<Driver> getAll();
}
