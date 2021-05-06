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


    public List<Employee> findEmployeeLists(String searchText, int pageIndex,int pageSize) {
        //查询的list
        List<EmployeePo> employeeList = employeeRedisDao.list(pageIndex, searchText,pageSize);

        if (employeeList.size() == 0) {
            //从数据库查数据
            employeeList = employeeDao.list(searchText);
            //存入redis的缓存中
            employeeRedisDao.addRedisEmployeeList(employeeList, pageIndex, searchText,pageSize);
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

    //查询总记录数
    public int count(String searchText) {
        return employeeDao.count(searchText);
    }

    //删除员工
    public void del(Integer id) {
        employeeDao.del(id);
        employeeRedisDao.updateRedis();
    }
}