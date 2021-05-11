package com.woniu.service.impl;

import com.woniu.adapter.CarImgAdapter;
import com.woniu.domain.CarImg;
import com.woniu.service.CarImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:23
 */
@Service
public class CarImgServiceImpl implements CarImgService {
    @Autowired
    private CarImgAdapter carImgAdapter;

    @Override
    public List<CarImg> queryByCarId(Integer carId) {
        return carImgAdapter.queryByCarId(carId);
    }

    @Override
    public void add(CarImg carImg) {
        carImgAdapter.add(carImg);
    }

    @Override
    public void update(CarImg carImg) {
        carImgAdapter.update(carImg);
    }

    @Override
    public void delete(Integer id) {
        carImgAdapter.delete(id);
    }
}
