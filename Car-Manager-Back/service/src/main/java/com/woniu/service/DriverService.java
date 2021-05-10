package com.woniu.service;


import com.woniu.domain.Driver;
import com.woniu.po.EmployeePo;

import java.util.List;

public interface DriverService {

    public List<Driver> driverList(String searchText,int pageIndex,int pageSize);

    int addDriver(Driver driver);

    int addDriverRole(Integer employeeId);

    int updateDriver(Driver driver);

    int delDriver(Integer id);

    int delDriverRole(Integer employeeId);

    int updateRole(Integer employeeId,Integer roleId);
    //获取驾驶员的中员工列表的信息
    EmployeePo getEmployee(String name );
    //获取驾驶员的姓名
    List<Driver> getAll();

    int count(String searchText);
}
