package com.woniu.controller;


import com.woniu.po.RolePo;
import com.woniu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping("/list")
    public List list(){
        List<RolePo> rolePos = roleService.roles();
        return rolePos;
    }
}
