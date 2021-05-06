package com.woniu.dao;

import com.woniu.po.EmployeePo;
import com.woniu.po.MenuPo;
import com.woniu.po.OppositeCompanyPo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/10:09
 * @Description: 员工Dao接口
 */

public interface EmployeeDao {

    //员工表查询
    List<EmployeePo> list(String searchText);

    //根据账号密码查询
    EmployeePo  getAccount(EmployeePo employeePo);

    //查询用户名的权限
    List<MenuPo> mens(String name);

    int count(String searchText);

    void del(Integer id);
}
