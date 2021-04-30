package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.CarDao;
import com.woniu.dao.RoleDao;
import com.woniu.po.Car;
import com.woniu.redis.CarRedisDao;
import com.woniu.redis.RoleRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:40
 */
@Component
public class CarAdapter {
    @Autowired
    private CarDao carDao;
    @Autowired
    private CarRedisDao carRedisDao;

    // 添加
    public int add(Car car){
        // 向数据库里添加car
        int n = carDao.add(car);
        // 从数据里查出所有car
        List<Car> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 修改
    public int update(Car car){
        int n = carDao.update(car);
        // 从数据里查出所有car
        List<Car> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 删除
    public int delete(Car car){
        int n = carDao.delete(car);
        // 从数据里查出所有car
        List<Car> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 根据id查
    public Car findById(Car car){
        return carDao.findById(car);
    }
    // 查询
    public List<Car> findCarList(){
        List<Car> carList = carRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(carList.size() == 0){
            //从数据库查数据
            carList = carDao.carList();
            //存入redis的缓存中
            carRedisDao.addRedisCarList(carList);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<Car> cars = new ArrayList<Car>();
        for(Car car : carList) {
            Car item = new Car();
            BeanUtils.copyProperties(car, item);
            cars.add(item);
        }
        return cars ;
    }
}
