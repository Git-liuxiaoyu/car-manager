package com.woniu.dao;

import com.woniu.po.Car;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface CarDao {
    public List<Car> carList();
    public int add(Car car);
    public int update(Car car);
    public int delete(Car car);
    public Car findById(Car car);
}
