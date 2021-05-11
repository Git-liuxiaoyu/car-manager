package com.woniu.adapter;
import com.woniu.dao.CarImgDao;
import com.woniu.domain.CarImg;
import com.woniu.po.CarImgPo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void delete(Integer id) {
        carImgDao.delete(id);
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
    // 查询
    public List<CarImg> queryByCarId(Integer carId){
        List<CarImgPo> carImgPoList = carImgDao.queryByCarId(carId);
        List<CarImg> carImgList = new ArrayList<>();
        for (CarImgPo e : carImgPoList) {
            CarImg item = new CarImg();
            BeanUtils.copyProperties(e, item);
            carImgList.add(item);
        }
        return carImgList;
    }

}
