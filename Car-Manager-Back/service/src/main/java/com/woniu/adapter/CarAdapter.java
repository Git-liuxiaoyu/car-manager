package com.woniu.adapter;


import com.github.pagehelper.PageHelper;
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

    public List<Car> getAll() {
        List<CarPo> carList = carDao.getAll();
        List<Car> cars = new ArrayList<>();
        for (CarPo e : carList) {
            Car item = new Car();
            BeanUtils.copyProperties(e, item);
            cars.add(item);
        }
        return cars;
    }

    public List<Car> list(String searchText, int pageIndex, int pageSize) {
        //查询的list
        List<CarPo> carList = carRedisDao.list(pageIndex, searchText, pageSize);

        if (carList.size() == 0) {
            //从数据库查数据
            PageHelper.startPage(pageIndex,pageSize);
            carList = carDao.list(searchText);
            //存入redis的缓存中
            carRedisDao.addRedisCarList(carList, pageIndex, searchText, pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<Car> cars = new ArrayList<>();
        for (CarPo e : carList) {
            Car item = new Car();
            BeanUtils.copyProperties(e, item);
            cars.add(item);
        }
        return cars;
    }

    //查询总记录数
    public int count(String searchText) {
        return carDao.count(searchText);
    }

    /**
     * 删除加油信息
     *
     * @param id
     * @return
     */
    public void delete(Integer id) {
        carDao.delete(id);
        carRedisDao.updateRedis();
    }


    /**
     * 添加驾驶员档案
     *
     * @param car
     * @return
     */
    public void add(Car car) {
        CarPo item = new CarPo();
        BeanUtils.copyProperties(car, item);
        carDao.add(item);
        carRedisDao.updateRedis();
    }

    /**
     * 修改加油信息
     *
     * @param car
     * @return
     */
    public void update(Car car) {
        CarPo item = new CarPo();
        BeanUtils.copyProperties(car, item);
        carDao.update(item);
        carRedisDao.updateRedis();
    }

    public Car getById(Integer id) {
        CarPo carPo = carDao.getById(id);
        Car car = new Car();
        BeanUtils.copyProperties(carPo, car);
        return car;
    }
}
