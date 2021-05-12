package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.woniu.dao.RoleDao;
import com.woniu.domain.Role;
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

    public List<Role> findAll(String searchText, int pageIndex, int pageSize) {
        List<RolePo> rolePoList = roleRedisDao.list(pageIndex, searchText,pageSize);
        ObjectMapper objectMapper = new ObjectMapper();
        if (rolePoList.size() == 0) {
            //从数据库查数据
            PageHelper.startPage(pageIndex, pageSize);
            rolePoList = roleDao.findAll(searchText);
            //存入redis的缓存中
            roleRedisDao.addRedisUserList(rolePoList,pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<Role> roles = new ArrayList<>();
        for (RolePo RolePo : rolePoList) {
            Role item = new Role();
            BeanUtils.copyProperties(RolePo, item);
            roles.add(item);
        }
        return roles;
    }

    //查询员工的角色
    public List<Role> getById(Integer id) {
        //查询
        List<RolePo> rolePoList = roleDao.getById(id);
        //创建service新的list对象
        List<Role> roles = new ArrayList<>();
        //对象转换
        for (RolePo RolePo : rolePoList) {
            Role item = new Role();
            BeanUtils.copyProperties(RolePo, item);
            roles.add(item);
        }
        return roles;
    }

    public void deleteById(int id) {
        roleDao.deleteById(id);
    }

    public void addRole(int id, int roleId) {
        roleDao.addRole(id, roleId);
    }

    public Integer count(String searchText) {
        return roleDao.count(searchText);
    }

    public List<Role> findRoleList() {
        List<RolePo> rolePoList = roleDao.roleList();
        List<Role> roles = new ArrayList<>();
        for (RolePo RolePo : rolePoList) {
            Role item = new Role();
            BeanUtils.copyProperties(RolePo, item);
            roles.add(item);
        }
        return roles;
    }

    public void add(Role role) {
        RolePo rolePo=new RolePo();
        BeanUtils.copyProperties(role, rolePo);
        roleDao.add(rolePo);
        roleRedisDao.updateRedis();
    }

    public void delete(Integer id) {
        roleDao.delete(id);
        roleRedisDao.updateRedis();
    }

    public List<Role> getByRoleId(Integer id) {
        List<RolePo> rolePoList = roleDao.getByRoleIdList(id);
        List<Role> roles = new ArrayList<>();
        for (RolePo RolePo : rolePoList) {
            Role item = new Role();
            BeanUtils.copyProperties(RolePo, item);
            roles.add(item);
        }
        return roles;
    }

    public void update(Role role) {
        RolePo rolePo=new RolePo();
        BeanUtils.copyProperties(role, rolePo);
        roleDao.update(rolePo);
        roleRedisDao.updateRedis();
    }
}
