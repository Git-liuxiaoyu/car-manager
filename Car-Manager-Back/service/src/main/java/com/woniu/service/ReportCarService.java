package com.woniu.service;


import com.woniu.domain.Driver;
import com.woniu.domain.ReportCar;
import com.woniu.po.EmployeePo;

import java.util.List;

public interface ReportCarService {

    List<ReportCar> getAll();

    ReportCar getOne(String carNum);
}
