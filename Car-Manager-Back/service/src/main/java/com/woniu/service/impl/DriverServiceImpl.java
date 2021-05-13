package com.woniu.service.impl;

import com.woniu.adapter.DriverAdapter;
import com.woniu.domain.Driver;
import com.woniu.po.EmployeePo;
import com.woniu.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DriverServiceImpl implements DriverService {


    @Autowired
    private DriverAdapter driverAdapter;


    @Override
    public List<Driver> driverList(String searchText,int pageIndex,int pageSize) {

        return driverAdapter.findDriverLists(searchText,pageIndex,pageSize);
    }

    @Override
    public int count(String searchText) {
        return driverAdapter.count(searchText);
    }




    @Override
    public int addDriver(Driver driver) {
        return driverAdapter.addDriver(driver);
    }

    @Override
    public int addDriverRole(Integer employeeId) {
        return driverAdapter.addDriverRole(employeeId);
    }

    @Override
    public int updateDriver(Driver driver) {
        return driverAdapter.updateDriver(driver);
    }

    @Override
    public int delDriver(Integer id) {
        return driverAdapter.delDriver(id);
    }

    @Override
    public int delDriverRole(Integer employeeId) {
        return driverAdapter.delDriverRole(employeeId);
    }

    @Override
    public EmployeePo getEmployee(String name) {
        return driverAdapter.getEmployee(name);
    }

    @Override
    public List<Driver> getAll() {
        return driverAdapter.getAll();
    }

    @Override
    public int updateRole(Integer employeeId,Integer roleId) {
        return driverAdapter.updateRole(employeeId,roleId);
    }

    @Override
    public EmployeePo getOne(String name) {
        return driverAdapter.getOne(name);
    }

    @Override
    public Driver getDriver(String name) {
        return driverAdapter.getDriver(name);
    }

//    @Override
//    public int addDriver(Driver driver) {
////        driverAdapter.addDriver(driver);
//        return driverAdapter.addDriver(driver);
//    }
}
