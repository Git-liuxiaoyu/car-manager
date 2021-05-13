package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
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

    public List<Driver> findDriverLists(String searchText, int pageIndex,int pageSize){
        List<DriverPo> driverList = driverRedisDao.list(pageIndex, searchText,pageSize);
//        ObjectMapper objectMapper = new ObjectMapper();
        if(driverList.size() == 0){
            //从数据库查数据
            PageHelper.startPage(pageIndex, pageSize);
            driverList = driverDao.findDriverList(searchText);
            //存入redis的缓存中
            driverRedisDao.addRedisDriverList(driverList,pageIndex, searchText,pageSize);
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

    //查询总记录数
    public int count(String searchText) {
        return driverDao.count(searchText);
    }


    public int addDriver(Driver driver){

        DriverPo item = new DriverPo();
        BeanUtils.copyProperties(driver, item);
        int i = driverDao.addDriver(item);
        if (i>0){
             updateredis();
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
            updateredis();
        }
        return 1;
    }

    public int delDriver(Integer id){

//        DriverPo item = new DriverPo();
//        BeanUtils.copyProperties(driver, item);
        int i = driverDao.delDriver(id);
        if (i>0){
            updateredis();
        }
        return 1;
    }

    public EmployeePo getEmployee(String name){
        return driverDao.getEmployee(name);
    }

    public int updateRole(Integer employeeId,Integer roleId){
        return driverDao.updateRole(employeeId,roleId);
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

    public EmployeePo getOne(String name){
//        Driver driver = new Driver();
//        DriverPo driverPo = driverDao.getOne(name);
//        if(driverPo!=null) {
//            BeanUtils.copyProperties(driverPo, driver);
//        }
//        return driver;
        EmployeePo employeePo=null;
        EmployeePo employeePo1 = driverDao.getOne(name);
        if (employeePo1!=null){
            employeePo=employeePo1;
        }
        return employeePo;
    }


    public Driver getDriver(String name){
        Driver driver = new Driver();
        DriverPo driverPo = driverDao.getDriver(name);
        if(driverPo!=null) {
            BeanUtils.copyProperties(driverPo, driver);
        }
        return driver;
    }


    public int addDriverRole(Integer employeeId){
        return driverDao.addDriverRole(employeeId);
    }

    public int delDriverRole(Integer employeeId){
        return driverDao.delDriverRole(employeeId);
    }

    /**
     * 更新redis
     */
    public void updateredis(){

        driverRedisDao.updateRedis();
    }

}
