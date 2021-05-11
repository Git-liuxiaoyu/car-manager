package com.woniu.service;

import com.woniu.domain.CarImg;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:22
 */

public interface CarImgService {
    // 查询
    List<CarImg> queryByCarId(Integer carId);
    // 添加
    void add(CarImg carImg);
    // 修改
    void update(CarImg carImg);
    // 删除
    void delete(Integer id);
}
