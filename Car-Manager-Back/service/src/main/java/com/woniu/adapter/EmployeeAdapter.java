package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.EmployeeDao;
import com.woniu.domain.Employee;
import com.woniu.po.EmployeePo;
import com.woniu.redis.EmployeeRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeAdapter {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeRedisDao employeeRedisDao;

    public List<Employee> findEmployeeLists(Employee employee) {
        //查询的list
        List<EmployeePo> employeeList = employeeRedisDao.list();
        if (employeeList.size() == 0) {
            //对象转换
            EmployeePo item = new EmployeePo();
            BeanUtils.copyProperties(employee, item);
            //从数据库查数据
            employeeList = employeeDao.list(item);
            //存入redis的缓存中
            employeeRedisDao.addRedisEmployeeList(employeeList);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<Employee> employees = new ArrayList<>();
        for (EmployeePo e : employeeList) {
            Employee item = new Employee();
            BeanUtils.copyProperties(e, item);
            employees.add(item);
        }
        return employees;
    }
}