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
    public List<Driver> driverList() {

        return driverAdapter.findDriverLists();
    }

    @Override
    public int addDriver(Driver driver) {
        return driverAdapter.addDriver(driver);
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
    public EmployeePo getEmployee(String name) {
        return driverAdapter.getEmployee(name);
    }

    @Override
    public int updateRole(Integer employeeId) {
        return driverAdapter.updateRole(employeeId);
    }

//    @Override
//    public int addDriver(Driver driver) {
////        driverAdapter.addDriver(driver);
//        return driverAdapter.addDriver(driver);
//    }
}
