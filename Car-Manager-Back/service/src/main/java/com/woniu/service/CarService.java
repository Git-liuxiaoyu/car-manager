package com.woniu.service;

import com.woniu.po.Car;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface CarService {
    public List<Car> carList();
    public int add(Car car);
    public int update(Car car);
    public int delete(Car car);
    public Car findById(Car car);
}
