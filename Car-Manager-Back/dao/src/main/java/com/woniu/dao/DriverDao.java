package com.woniu.dao;

import com.woniu.po.DriverPo;
import com.woniu.po.EmployeePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DriverDao {
    //查询驾驶员信息
    List<DriverPo> findDriverList(String searchText);

    //添加驾驶员信息
    int addDriver(DriverPo driverPo);

    //添加一条驾驶员角色
    int addDriverRole(Integer employeeId);

    //修改驾驶员信息
    int updateDriver(DriverPo driverPo);

    // 删除驾驶员信息
    int delDriver(Integer id);
    //删除该条驾驶员角色del
    int delDriverRole(Integer employeeId);

    //获得员工信息
    EmployeePo getEmployee(String name);

    int updateRole(Integer employeeId,Integer roleId);
//    获得所有信息
    List<DriverPo> getAll();

    int count(String searchText);
    //查询所有员工
    EmployeePo getOne(@Param("name") String name);
//    查询所有驾驶员
    DriverPo getDriver(String name);

}
