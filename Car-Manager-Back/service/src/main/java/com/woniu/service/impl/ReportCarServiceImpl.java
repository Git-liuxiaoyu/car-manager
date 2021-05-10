package com.woniu.service.impl;

import com.woniu.adapter.DriverAdapter;
import com.woniu.adapter.ReportCarAdapter;
import com.woniu.domain.Driver;
import com.woniu.domain.ReportCar;
import com.woniu.po.EmployeePo;
import com.woniu.service.DriverService;
import com.woniu.service.ReportCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ReportCarServiceImpl implements ReportCarService {


    @Autowired
    private ReportCarAdapter reportCarAdapter;


    @Override
    public List<ReportCar> getAll() {
        return reportCarAdapter.getAll();
    }

    @Override
    public ReportCar getOne(String carNum) {
        return reportCarAdapter.getOne(carNum);
    }
}
