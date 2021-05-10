package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Employee;
import com.woniu.jwt.JwtTokenUtil;
import com.woniu.po.EmployeePo;
import com.woniu.po.MenuPo;
import com.woniu.service.EmployeeService;
import com.woniu.util.ResponseResult;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    public ResponseResult login(@RequestBody EmployeePo employee) throws Exception {
        //查询账号密码
        EmployeePo e = employeeService.getAccount(employee);
        if (e == null) {
            return ResponseResult.ADNE;//账号不存在
        } else if (!e.getPassword().equals(employee.getPassword())) {
            return ResponseResult.WP;//密码不存在
        } else {
            String token = JwtTokenUtil.createSign(e.getName());
            return new ResponseResult(200, token);//创建一个token，返回给客户端
        }
    }

    @RequestMapping("/menu")
    public ResponseResult<List<MenuPo>> menu(String token) {
        //获取用户名
        String name = JwtTokenUtil.getUserId(token);

        List<MenuPo> mens = employeeService.mens(name);

        List<MenuPo> menuPos = new ArrayList<>();//定义一个父列表的集合

        for (MenuPo p : mens) {
            if (p.getParentId() == null) {
                menuPos.add(p);//满足需求的存进父集合列表
            }
        }

        for(MenuPo p:menuPos){
            List<MenuPo> er = new ArrayList<>();//放儿子的集合
            for(MenuPo item : mens){
                if (item.getParentId() == p.getId()) {//如果循环出来的Parentid等于我们的父列表的id
                    er.add(item);
                }
            }
            p.setChildren(er);
        }
        return new ResponseResult<List<MenuPo>>(menuPos);
    }

    @RequestMapping("/list")
    public ResponseResult<PageInfo<Employee>> employeeList(Integer p, String searchText,Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=employeeService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<Employee> employeeList = employeeService.findAll(searchText,pageIndex,pageSize);
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/del")
    public void employeeDel(Integer id){
        employeeService.delById(id);
    }

    //添加用户
    @RequestMapping("/add")
    public ResponseResult employeeAdd(@RequestBody Employee employee){
        employeeService.add(employee);
        return ResponseResult.SUCCESS;
    }
    //修改用户
    @RequestMapping("/update")
    public ResponseResult employeeUpdate(@RequestBody Employee updateEmployee){
        employeeService.update(updateEmployee);
        return ResponseResult.SUCCESS;
    }

    //查询用户的密码
    @RequestMapping("/getByAccount")
    public ResponseResult getByAccount(String account){
        Employee e=employeeService.getByAccount(account);
        return new ResponseResult( );
    }

    //修改用户的密码
    @RequestMapping("/updatePassWord")
    public ResponseResult updatePassWord(@RequestBody Employee employee){
        employeeService.updatePassWord(employee);
        return ResponseResult.SUCCESS;
    }

    //查询所有员工
    @RequestMapping("/getAll")
    public ResponseResult<List<Employee>> getAll(){
       List<Employee> employeeList=employeeService.getAll();
        return new ResponseResult(employeeList);
    }
}
