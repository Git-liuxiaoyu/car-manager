package com.woniu.adapter;

import com.woniu.dao.DriverDao;
import com.woniu.dao.ReportCarDao;
import com.woniu.domain.Driver;
import com.woniu.domain.Employee;
import com.woniu.domain.ReportCar;
import com.woniu.domain.Role;
import com.woniu.po.DriverPo;
import com.woniu.po.EmployeePo;
import com.woniu.po.ReportCarPo;
import com.woniu.po.RolePo;
import com.woniu.redis.DriverRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportCarAdapter {

    @Autowired
    private ReportCarDao reportCarDao;

    @Autowired
    private DriverRedisDao driverRedisDao;

   public List<ReportCar> getAll(){
       List<ReportCarPo> reportCarPos = reportCarDao.getAll();

       List<ReportCar> reportCarList = new ArrayList<>();
       for (ReportCarPo  reportCarPo: reportCarPos) {
           ReportCar item = new ReportCar();
           BeanUtils.copyProperties(reportCarPo, item);
           reportCarList.add(item);
       }
       return reportCarList;
   }

    public ReportCar getOne(Integer carId){
//          ReportCarPo reportCarPo = new ReportCarPo();
        ReportCarPo reportCarPo = reportCarDao.getOne(carId);
        ReportCar reportCar = new ReportCar();
            BeanUtils.copyProperties(reportCarPo, reportCar);

        return reportCar;
    }

    public Integer getDepartureNum(Integer carId){
        Integer num = reportCarDao.getDepartureNum(carId);

        return num;
    }




}
