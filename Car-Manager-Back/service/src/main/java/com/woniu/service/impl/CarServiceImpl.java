package com.woniu.service.impl;

import com.woniu.adapter.CarAdapter;
import com.woniu.dao.CarDao;
import com.woniu.domain.Car;
import com.woniu.domain.Car;
import com.woniu.po.CarPo;
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
    public List<Car> getAll() {
        return carAdapter.getAll();
    }

    @Override
    public List<Car> list(String searchText, int pageIndex, int pageSize) {

        return carAdapter.list(searchText,pageIndex,pageSize);
    }

    @Override
    public void add(Car departureRecord) {
        carAdapter.add(departureRecord);

    }

    @Override
    public void update(Car departureRecord) {
        carAdapter.update(departureRecord);
    }
    @Override
    public int count(String searchText) {
        return carAdapter.count(searchText);
    }
    @Override
    public void delete(Integer id) {
        carAdapter.delete(id);
    }
}
