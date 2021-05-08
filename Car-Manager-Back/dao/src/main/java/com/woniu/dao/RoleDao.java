package com.woniu.dao;

import com.woniu.po.RolePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/29/16:48
 * @Description:
 */
public interface RoleDao {
    public List<RolePo> roleList();

    List<RolePo> getById(Integer id);

    void deleteById(int id);

    void addRole(@Param("id") int id, @Param("roleId") int roleId);

    Integer count(String searchText);

    List<RolePo> findAll(String searchText);

    void add(RolePo rolePo);

    void delete(Integer id);

    List<RolePo> getByRoleIdList(Integer id);

    void update(RolePo rolePo);
}
