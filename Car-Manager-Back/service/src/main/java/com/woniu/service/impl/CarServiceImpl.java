package com.woniu.service.impl;

import com.woniu.adapter.CarAdapter;
import com.woniu.dao.CarDao;
import com.woniu.po.Car;
import com.woniu.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:37
 */
@Component
public class CarServiceImpl implements CarService {
    @Autowired
    private CarAdapter carAdapter;
    @Override
    public List<Car> carList() {
        return carAdapter.findCarList();
    }

    @Override
    public int add(Car car) {
        return carAdapter.add(car);
    }

    @Override
    public int update(Car car) {
        return carAdapter.update(car);
    }

    @Override
    public int delete(Car car) {
        return carAdapter.delete(car);
    }

    @Override
    public Car findById(Car car) {
        return carAdapter.findById(car);
    }
}
