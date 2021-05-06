package com.woniu.service;


import com.woniu.domain.Driver;
import com.woniu.po.EmployeePo;

import java.util.List;

public interface DriverService {

    public List<Driver> driverList();

    int addDriver(Driver driver);

    int updateDriver(Driver driver);

    int delDriver(Integer id);

    EmployeePo getEmployee(String name);

    int updateRole(Integer employeeId);
}
