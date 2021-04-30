package com.woniu.dao;

import com.woniu.po.CarPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface CarDao {
    public List<CarPo> carList();
    public int add(CarPo carPo);
    public int update(CarPo carPo);
    public int delete(CarPo carPo);
    public CarPo findById(CarPo carPo);
}
