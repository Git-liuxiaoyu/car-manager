package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.RoleDao;
import com.woniu.po.RolePo;
import com.woniu.redis.RoleRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAdapter {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleRedisDao roleRedisDao;

    public List<RolePo> findRoleLists(){
        List<RolePo> rolePoList = roleRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rolePoList.size() == 0){
            //从数据库查数据
            rolePoList = roleDao.roles();
            //存入redis的缓存中
            roleRedisDao.addRedisUserList(rolePoList);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<RolePo> rolePos = new ArrayList<RolePo>();
        for(RolePo RolePo : rolePoList) {
            RolePo item = new RolePo();
            BeanUtils.copyProperties(RolePo, item);
            rolePos.add(item);
        }
        return rolePos;
    }
}
