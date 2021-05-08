package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Role;
import com.woniu.service.RoleService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //分页查询的数据
    @RequestMapping("/list")
    public ResponseResult<PageInfo<Role>> roleList(Integer p, String searchText, Integer size) {
        int pageIndex = 1;
        int pageSize = 5;
        if (p >= 1) {
            pageIndex = p;
        }
        if (pageSize >= 5) {
            pageSize = size;
        }
        Integer total = roleService.count(searchText);
        PageHelper.startPage(pageIndex, pageSize);
        List<Role> employeeList = roleService.findAll(searchText, pageIndex, pageSize);
        PageInfo<Role> pageInfo = new PageInfo<>(employeeList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    //不分页查询list
    @RequestMapping("/lists")
    public List lists() {
        List<Role> roles = roleService.roleList();
        return roles;
    }

    //查询用户拥有多少角色
    @RequestMapping("/doRole")
    public ResponseResult<List<Role>> doRole(Integer id) {
        List<Role> roles = roleService.getById(id);
        return new ResponseResult(roles);
    }

    //添加用户角色权限
    @RequestMapping("/addRole")
    public ResponseResult addRole(Integer id, String[] roleId) {
        //删除用户的所有角色
        roleService.deleteById(id);
        for (String r : roleId) {
            roleService.addRole(id, Integer.parseInt(r));
        }
        return ResponseResult.SUCCESS;
    }

    //添加角色
    @RequestMapping("/add")
    public ResponseResult add(@RequestBody Role role) {
        roleService.add(role);
        return ResponseResult.SUCCESS;
    }

    //删除角色
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id) {
        //如果删除的此角色有对应的员工是不可以删除的，那么要先查询一下
       List<Role> roleList=roleService.getByRoleId(id);
       if(roleList.size()!=0){
           return new ResponseResult(501,"已有员工又要此角色无法删除该角色");
       }
        roleService.delete(id);
        return ResponseResult.SUCCESS;
    }
    //修改角色
    @RequestMapping("/update")
    public ResponseResult update(@RequestBody Role role) {
        roleService.update(role);
        return ResponseResult.SUCCESS;
    }
}
