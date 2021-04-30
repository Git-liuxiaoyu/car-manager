package com.woniu.service;

import com.woniu.po.CarPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface CarService {
    public List<CarPo> carList();
    public int add(CarPo carPo);
    public int update(CarPo carPo);
    public int delete(CarPo carPo);
    public CarPo findById(CarPo carPo);
}
