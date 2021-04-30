package com.woniu.service.impl;

import com.woniu.adapter.CarAdapter;
import com.woniu.dao.CarDao;
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
    public List<CarPo> carList() {
        return carAdapter.findCarList();
    }

    @Override
    public int add(CarPo carPo) {
        return carAdapter.add(carPo);
    }

    @Override
    public int update(CarPo carPo) {
        return carAdapter.update(carPo);
    }

    @Override
    public int delete(CarPo carPo) {
        return carAdapter.delete(carPo);
    }

    @Override
    public CarPo findById(CarPo carPo) {
        return carAdapter.findById(carPo);
    }
}
