package com.woniu.service.impl;

import com.woniu.adapter.EmployeeAdapter;
import com.woniu.dao.EmployeeDao;
import com.woniu.po.EmployeePo;
import com.woniu.po.MenuPo;
import com.woniu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/10:26
 * @Description:
 */

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeAdapter employeeAdapter;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public EmployeePo getAccount(EmployeePo employee) {
        return employeeDao.getAccount(employee);
    }

    @Override
    public List<MenuPo> mens(String name) {
        return employeeDao.mens(name);
    }
}
