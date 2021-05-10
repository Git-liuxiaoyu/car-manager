package com.woniu.service;

import com.woniu.domain.CarImg;

/**
 * @Author Administrator
 * @Date 2021/5/10 21:22
 */

public interface CarImgService {
    // 查询
    CarImg query(CarImg carImg);
    // 添加
    void add(CarImg carImg);
    // 修改
    void update(CarImg carImg);
    // 删除
    void delete(CarImg carImg);
}
