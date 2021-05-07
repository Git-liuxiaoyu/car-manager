package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DriverDao;
import com.woniu.domain.Driver;
import com.woniu.domain.Employee;
import com.woniu.po.CarPo;
import com.woniu.po.DriverPo;
import com.woniu.po.EmployeePo;
import com.woniu.redis.DriverRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DriverAdapter {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverRedisDao driverRedisDao;

    public List<Driver> findDriverLists(){
        List<DriverPo> driverList = driverRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(driverList.size() == 0){
            //从数据库查数据
            driverList = driverDao.findDriverList();
            //存入redis的缓存中
            driverRedisDao.addRedisDriverList(driverList);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<Driver> drivers = new ArrayList<Driver>();
        for(DriverPo driver : driverList) {
            Driver item = new Driver();
            Employee employee = new Employee();
            BeanUtils.copyProperties(driver.getEmployeePo(), employee);
            BeanUtils.copyProperties(driver, item);
            item.setEmployee(employee);
            drivers.add(item);
        }
        return drivers;
    }

    public int addDriver(Driver driver){

        DriverPo item = new DriverPo();
        BeanUtils.copyProperties(driver, item);
        int i = driverDao.addDriver(item);
        if (i>0){
            List<DriverPo> driverList =  driverDao.findDriverList();
            //存入redis的缓存中
            driverRedisDao.addRedisDriverList(driverList);

        }
        return 1;

    }

    /**
     * 修改
     * @param driver
     * @return
     */
    public int updateDriver(Driver driver){

        DriverPo item = new DriverPo();
        BeanUtils.copyProperties(driver, item);
        int i = driverDao.updateDriver(item);
        if (i>0){
            List<DriverPo> driverList =  driverDao.findDriverList();
            //存入redis的缓存中
            driverRedisDao.addRedisDriverList(driverList);
        }
        return 1;
    }

    public int delDriver(Integer id){

//        DriverPo item = new DriverPo();
//        BeanUtils.copyProperties(driver, item);
        int i = driverDao.delDriver(id);
        if (i>0){
            List<DriverPo> driverList =  driverDao.findDriverList();
            //存入redis的缓存中
            driverRedisDao.addRedisDriverList(driverList);
        }
        return 1;
    }

    public EmployeePo getEmployee(String name){
        return driverDao.getEmployee(name);
    }

    public int updateRole(Integer employeeId){
        return driverDao.updateRole(employeeId);
    }


    //获得所有驾驶员姓名
    public List<Driver> getAll(){
        List<DriverPo> driverPos = driverDao.getAll();

        List<Driver> driverList = new ArrayList<>();
        for (DriverPo driverPo : driverPos) {
            Driver item = new Driver();
            BeanUtils.copyProperties(driverPo, item);
            driverList.add(item);
        }
       return driverList;

    }

}
