package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.woniu.dao.InsureRecordDao;
import com.woniu.domain.KeepRecord;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.KeepRecordPo;
import com.woniu.redis.InsureRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:40
 */
@Component
public class InsureRecordAdapter {
    @Autowired
    private InsureRecordDao insureRecordDao;
    @Autowired
    private InsureRecordRedisDao insureRecordRedisDao;

    // 添加
    public int add(InsureRecordPo insureRecordPo){
        // 向数据库里添加insureRecord
        int n = insureRecordDao.add(insureRecordPo);
        //更新redis
        insureRecordRedisDao.updateRedis();
        return n;
    }
    // 修改
    public int update(InsureRecordPo insureRecordPo){
        int n = insureRecordDao.update(insureRecordPo);

        //更新redis
        insureRecordRedisDao.updateRedis();
        return n;
    }
    // 删除
    public int delete(InsureRecordPo insureRecord){
        int n = insureRecordDao.delete(insureRecord);
        //更新redis
        insureRecordRedisDao.updateRedis();
        return n;
    }

    // 根据id查
    public InsureRecordPo findById(InsureRecordPo insureRecord){
        return insureRecordDao.findById(insureRecord);
    }



    //分页查询
    public List<InsureRecordPo> findInsureRecordList(String searchText, int pageIndex, int pageSize) {
        //查询的list
        List<InsureRecordPo> insureRecordList = insureRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (insureRecordList.size() == 0) {
            //从数据库查数据
            PageHelper.startPage(pageIndex, pageSize);
            insureRecordList = insureRecordDao.insureRecordList(searchText);
            //存入redis的缓存中
            insureRecordRedisDao.addRedisInsureRecordList(insureRecordList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<InsureRecordPo> insureRecords = new ArrayList<>();
        for (InsureRecordPo insureRecordPo : insureRecordList) {
            InsureRecordPo item = new InsureRecordPo();
            BeanUtils.copyProperties(insureRecordPo, item);
            insureRecords.add(item);
        }
        return insureRecords;
    }

    //查询总记录数
    public int count(String searchText) {
        return insureRecordDao.count(searchText);
    }








}
