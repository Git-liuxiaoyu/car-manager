package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.woniu.dao.OilRecordDao;
import com.woniu.dao.OilRecordDao;
import com.woniu.domain.Employee;
import com.woniu.domain.OilRecord;
import com.woniu.po.*;
import com.woniu.redis.OilRecordRedisDao;
import com.woniu.redis.OilRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OilRecordAdapter {
    @Autowired
    private OilRecordDao oilRecordDao;

    @Autowired
    private OilRecordRedisDao oilRecordRedisDao;

    public List<OilRecord> findLists(String searchText, int pageIndex, int pageSize) {
        //查询的list
        List<OilRecordPo> oilRecordPoList = oilRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (oilRecordPoList.size() == 0) {
            //从数据库查数据
            PageHelper.startPage(pageIndex, pageSize);
            oilRecordPoList = oilRecordDao.findList(searchText);
            //存入redis的缓存中
            oilRecordRedisDao.addRedisList(oilRecordPoList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<OilRecord> oilRecords = new ArrayList<>();
        for (OilRecordPo e : oilRecordPoList) {
            OilRecord item = new OilRecord();
            BeanUtils.copyProperties(e, item);
            oilRecords.add(item);
        }
        return oilRecords;
    }









    /**
     * 添加加油记录
     * @param oilRecord
     * @return
     */
    public int add(OilRecord oilRecord){

        OilRecordPo item = new OilRecordPo();
        BeanUtils.copyProperties(oilRecord, item);
        int i = oilRecordDao.add(item);
        if (i>0){
            //清空redis
            oilRecordRedisDao.updateRedis();
        }
        return i;
    }

    /**
     * 根据id查询
     * @return
     */
    public OilRecordPo findbyid(OilRecordPo orpo){
        return oilRecordDao.findbyid(orpo);
    }
    /**
     * 修改加油信息
     * @param oilRecord
     * @return
     */
    public int update(OilRecord oilRecord){

        OilRecordPo item = new OilRecordPo();
        BeanUtils.copyProperties(oilRecord, item);
        int i = oilRecordDao.update(item);
        if (i>0){
            //清空redis
            oilRecordRedisDao.updateRedis();
        }
        return i;
    }

    //查询总记录数
    public int count(String searchText) {
        return oilRecordDao.count(searchText);
    }

    /**
     * 删除加油信息
     * @param id
     * @return
     */
    public int delete(Integer id){

//        OilRecordPo item = new OilRecordPo();
//        BeanUtils.copyProperties(oilRecord, item);
        int i = oilRecordDao.delete(id);
        if (i>0){
            //清空redis
            oilRecordRedisDao.updateRedis();
        }
        return i;
    }

    /**
     * 查询车牌号
     * @return
     */
    public List<CarPo>carlist(){
        List<CarPo> carlist = oilRecordDao.carlist();
        return carlist;
    }

    /**
     * 查询往来单位下拉框
     * @return
     */
    public List<OppositeCompanyPo> oppolist(Integer type){
        List<OppositeCompanyPo> oppolist = oilRecordDao.oppolist(type);

        return oppolist;
    }

    /**
     * 查询加油人员下拉框
     * @return
     */
    public List<DriverPo> driverlist(){
        List<DriverPo> driverList = oilRecordDao.driverlist();

        return driverList;
    }


}
