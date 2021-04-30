package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.OppositeCompanyDao;
import com.woniu.domain.OppositeCompany;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.redis.OppositeCompanyRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OppositeCompanyAdapter {

    @Autowired
    private OppositeCompanyRedisDao oppositeCompanyRedisDao;

    @Autowired
    private OppositeCompanyDao oppositeCompanyDao;

    /**
     * 查询列表
     * @return
     */
    public List<OppositeCompany> findoppositeCompanyLists(){
        List<OppositeCompanyPo> OppositeList = oppositeCompanyRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(OppositeList.size() == 0){
            //从数据库查数据
            OppositeList = oppositeCompanyDao.list();
            //存入redis的缓存中
            oppositeCompanyRedisDao.addRedisUserList(OppositeList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<OppositeCompany> Roles = new ArrayList<OppositeCompany>();
        for(OppositeCompanyPo ocpo : OppositeList) {
            OppositeCompany item = new OppositeCompany();
            BeanUtils.copyProperties(ocpo, item);
            Roles.add(item);
        }
        return Roles;
    }


    /**
     * 添加往来单位
     * @return
     */
    public int add(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.add(ocpo);
        if(i > 0){updateredis();}
        return i;
    }


    /**
     * 修改往来单位列表
     * @return
     */
    public int update(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.update(ocpo);
        //修改成功
        if(i > 0){updateredis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.delete(ocpo);
        //删除成功
        if(i>0){ updateredis();}
        return i;
    }



    /**
     * 更新redis
     */
    public void updateredis(){

        List<OppositeCompanyPo> list = oppositeCompanyDao.list();
        //修改redis的值
        oppositeCompanyRedisDao.addRedisUserList(list);
    }
}
