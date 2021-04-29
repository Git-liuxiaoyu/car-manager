package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.RoleDao;
import com.woniu.po.Role;
import com.woniu.redis.RoleRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAdapter {

    @Autowired
    private RoleDao RoleDao;

    @Autowired
    private RoleRedisDao roleRedisDao;

    public List<Role> findRoleLists(){
        List<Role> roleList = roleRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(roleList.size() == 0){
            //从数据库查数据
            roleList = RoleDao.roles();
            //存入redis的缓存中
            roleRedisDao.addRedisUserList(roleList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<Role> Roles = new ArrayList<Role>();
        for(Role Role : roleList) {
            Role item = new Role();
            BeanUtils.copyProperties(Role, item);
            Roles.add(item);
        }
        return Roles ;
    }
}
