package com.woniu.dao;

import com.woniu.po.CarPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */
public interface CarDao {
    // 查询所有
    List<CarPo> getAll();
    // 模糊查询
    List<CarPo> list(String searchText);
    // 添加
    void add(CarPo carPo);
    // 修改
    void update(CarPo carPo);
    // 计数
    Integer count(String searchText);
    // 删除
    void delete(Integer id);

    CarPo getById(Integer id);
}
