package com.woniu.dao;

import com.woniu.po.DriverPo;
import com.woniu.po.EmployeePo;

import java.util.List;

public interface DriverDao {
    //查询驾驶员信息
    List<DriverPo> findDriverList();

    //添加驾驶员信息
    int addDriver(DriverPo driverPo);
//
    //修改驾驶员信息
    int updateDriver(DriverPo driverPo);
//
    // 删除驾驶员信息
    int delDriver(Integer id);

    //获得员工信息
    EmployeePo getEmployee(String name);

    int updateRole(Integer employeeId);

}
