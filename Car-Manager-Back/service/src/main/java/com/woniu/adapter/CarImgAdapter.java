package com.woniu.adapter;
import com.woniu.dao.CarImgDao;
import com.woniu.domain.CarImg;
import com.woniu.po.CarImgPo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:40
 */
@Component
public class CarImgAdapter {
    @Autowired
    private CarImgDao carImgDao;

    public void delete(CarImg carImg) {
        CarImgPo item = new CarImgPo();
        BeanUtils.copyProperties(carImg, item);
        carImgDao.delete(item);
    }
    public void add(CarImg carImg){
        CarImgPo item = new CarImgPo();
        BeanUtils.copyProperties(carImg, item);
        carImgDao.add(item);
    }
    public void update(CarImg carImg){
        CarImgPo item = new CarImgPo();
        BeanUtils.copyProperties(carImg, item);
        carImgDao.update(item);
    }
    public CarImg query(CarImg carImg){
        CarImgPo carImgPo = new CarImgPo();
        BeanUtils.copyProperties(carImg, carImgPo);
        CarImgPo carImgPo1=  carImgDao.query(carImgPo);
        CarImg carImg1 = new CarImg();
        BeanUtils.copyProperties(carImgPo1, carImg1);
        return carImg1;
    }

}
