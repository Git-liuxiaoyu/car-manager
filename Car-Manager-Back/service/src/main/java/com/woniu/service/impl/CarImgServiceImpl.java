package com.woniu.service.impl;

import com.woniu.adapter.CarImgAdapter;
import com.woniu.domain.CarImg;
import com.woniu.service.CarImgService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:23
 */

public class CarImgServiceImpl implements CarImgService {
    @Autowired
    private CarImgAdapter carImgAdapter;
    @Override
    public CarImg query(CarImg carImg) {
        return carImgAdapter.query(carImg);
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
    public void delete(CarImg carImg) {
        carImgAdapter.delete(carImg);
    }
}
