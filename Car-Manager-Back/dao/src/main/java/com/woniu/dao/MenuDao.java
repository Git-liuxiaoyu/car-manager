package com.woniu.dao;

import com.woniu.po.MenuPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/05/08/16:34
 * @Description: 菜单权限表
 */
public interface MenuDao {
    List<MenuPo> menuPoList();
    @Select("delete from role_menu where roleId = #{roleId}")
    public void deletePermsByRoleId(Integer id);
    @Insert("insert into role_menu values(#{roleId},#{menuId})")
    public void addRight(Map<String,Integer> map);
    @Select("select rm.menuId from role_menu rm,menu m where rm.roleId= #{roleId} and rm.menuId = m.id and m.parentId != ''")
    public List<Integer> getPermsByRoleId(Integer roleId);
}
