package com.woniu.dao;

import com.woniu.po.CarImgPo;
import com.woniu.po.CarPo;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:01
 */

public interface CarImgDao {
    // 查询
    CarImgPo query(CarImgPo carImgPo);
    // 添加
    void add(CarImgPo carImgPo);
    // 修改
    void update(CarImgPo carImgPo);
    // 删除
    void delete(CarImgPo carImgPo);

}
