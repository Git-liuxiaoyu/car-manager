package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.CarDao;
import com.woniu.domain.Car;
import com.woniu.po.CarPo;
import com.woniu.redis.CarRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public int add(CarPo carPo){
        // 向数据库里添加car
        int n = carDao.add(carPo);
        // 从数据里查出所有car
        List<CarPo> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 修改
    public int update(CarPo carPo){
        int n = carDao.update(carPo);
        // 从数据里查出所有car
        List<CarPo> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 删除
    public int delete(CarPo car){
        int n = carDao.delete(car);
        // 从数据里查出所有car
        List<CarPo> carList = carDao.carList();
        //存入redis的缓存中
        carRedisDao.addRedisCarList(carList);
        return n;
    }
    // 根据id查
    public CarPo findById(CarPo car){
        return carDao.findById(car);
    }
    // 查询
    public List<CarPo> findCarList(){
        List<CarPo> carList = carRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(carList.size() == 0){
            //从数据库查数据
            carList = carDao.carList();
            //存入redis的缓存中
            carRedisDao.addRedisCarList(carList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<CarPo> cars = new ArrayList<CarPo>();
        for(CarPo carPo : carList) {
            CarPo item = new CarPo();
            BeanUtils.copyProperties(carPo, item);
            cars.add(item);
        }
        return cars ;
    }
}
