package com.woniu.service;

import com.woniu.dao.CarDao;
import com.woniu.domain.Car;
import com.woniu.po.CarPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface CarService {
    // 查询所有
    List<Car> getAll();
    // 模糊查询
    List<Car> list(String searchText,int pageIndex,int pageSize);
    // 添加
    void add(Car car);
    // 修改
    void update(Car car);
    // 计数
    int count(String searchText);
    // 删除
    void delete(Integer id);
}
