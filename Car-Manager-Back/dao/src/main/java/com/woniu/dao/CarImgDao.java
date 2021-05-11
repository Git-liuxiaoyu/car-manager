package com.woniu.dao;

import com.woniu.po.CarImgPo;
import com.woniu.po.CarPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:01
 */

public interface CarImgDao {
    // 查询
    List<CarImgPo> queryByCarId(Integer carId);
    // 添加
    void add(CarImgPo carImgPo);
    // 修改
    void update(CarImgPo carImgPo);
    // 删除
    void delete(Integer Id);

}
