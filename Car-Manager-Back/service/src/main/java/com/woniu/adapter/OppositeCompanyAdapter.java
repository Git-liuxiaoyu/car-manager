package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.OppositeCompanyDao;
import com.woniu.domain.OppositeCompany;
import com.woniu.po.InsureRecordPo;
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

    //分页查询
    public List<OppositeCompany> findoppositeCompanyLists(String searchText, int pageIndex, int pageSize) {
        //查询的list
        List<OppositeCompanyPo> OppositeList = oppositeCompanyRedisDao.list(pageIndex, searchText,pageSize);

        if (OppositeList.size() == 0) {
            //从数据库查数据
            OppositeList = oppositeCompanyDao.list(searchText);
            //存入redis的缓存中
            oppositeCompanyRedisDao.addRedisUserList(OppositeList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<OppositeCompany> Roles = new ArrayList<OppositeCompany>();
        for (OppositeCompanyPo ocpo : OppositeList) {
            OppositeCompany item = new OppositeCompany();
            BeanUtils.copyProperties(ocpo, item);
            Roles.add(item);
        }
        return Roles;
    }

    //查询总记录数
    public int count(String searchText) {
        return oppositeCompanyDao.count(searchText);
    }










    /**
     * 添加往来单位
     * @return
     */
    public int add(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.add(ocpo);
        if(i > 0){oppositeCompanyRedisDao.updateRedis();}
        return i;
    }


    /**
     * 修改往来单位列表
     * @return
     */
    public int update(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.update(ocpo);
        //修改成功
        if(i > 0){oppositeCompanyRedisDao.updateRedis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(OppositeCompanyPo ocpo){
        int i = oppositeCompanyDao.delete(ocpo);
        //删除成功
        if(i>0){oppositeCompanyRedisDao.updateRedis();}
        return i;
    }



    /**
     * 根据id查询往来单位
     * @param ocpo
     * @return
     */
    public OppositeCompanyPo findbyid(OppositeCompanyPo ocpo) {
        OppositeCompanyPo findbyid = oppositeCompanyDao.findbyid(ocpo);


        return findbyid;
    }
}
