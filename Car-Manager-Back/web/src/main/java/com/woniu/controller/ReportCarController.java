package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Driver;
import com.woniu.domain.ReportCar;
import com.woniu.po.EmployeePo;
import com.woniu.service.DriverService;
import com.woniu.service.EmployeeService;
import com.woniu.service.ReportCarService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/reportCar")
public class ReportCarController {

    @Autowired
    private ReportCarService reportCarService;
    @RequestMapping("all")
    public ResponseResult<List<ReportCar>> getAll(){
        List<ReportCar> reportCars = reportCarService.getAll();

        return new ResponseResult(reportCars);
    }

    @RequestMapping("one")
    public ResponseResult getOne(String carNum){
        ReportCar reportCar = reportCarService.getOne(carNum);
        Map<String, Object> objectHashMap = new HashMap<>();
        BigDecimal[] fee={
                reportCar.getAccidentFee(),
                reportCar.getChargesFee(),
                reportCar.getInsureFee(),
                reportCar.getKeepFee(),
                reportCar.getRepairFee(),
                reportCar.getOilFee(),
                reportCar.getYearCheckFee(),
                reportCar.getViolationFee(),
        };
        String[] name={
                "事故费用",
                "规费费用",
                "保险费用",
                "保养费用",
                "维修费用",
                "油费用",
                "年检费用",
                "违章费用",
        };
        objectHashMap.put( "事故费用", reportCar.getAccidentFee());
        objectHashMap.put( "规费费用", reportCar.getChargesFee());
        objectHashMap.put( "保险费用",  reportCar.getInsureFee());
        objectHashMap.put( "保养费用", reportCar.getKeepFee());
        objectHashMap.put( "维修费用", reportCar.getRepairFee());
        objectHashMap.put( "油费用", reportCar.getOilFee());
        objectHashMap.put( "年检费用", reportCar.getYearCheckFee());
        objectHashMap.put( "违章费用", reportCar.getViolationFee());
//        objectHashMap.put("fee",fee);
        return new ResponseResult(objectHashMap);
    }
}
