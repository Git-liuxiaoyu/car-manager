package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.InsureRecordDao;
import com.woniu.po.InsureRecordPo;
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
        // 从数据里查出所有insureRecord
        List<InsureRecordPo> insureRecordList = insureRecordDao.insureRecordList();
        //存入redis的缓存中
        insureRecordRedisDao.addRedisInsureRecordList(insureRecordList);
        return n;
    }
    // 修改
    public int update(InsureRecordPo insureRecordPo){
        int n = insureRecordDao.update(insureRecordPo);
        // 从数据里查出所有insureRecord
        List<InsureRecordPo> insureRecordList = insureRecordDao.insureRecordList();
        //存入redis的缓存中
        insureRecordRedisDao.addRedisInsureRecordList(insureRecordList);
        return n;
    }
    // 删除
    public int delete(InsureRecordPo insureRecord){
        int n = insureRecordDao.delete(insureRecord);
        // 从数据里查出所有insureRecord
        List<InsureRecordPo> insureRecordList = insureRecordDao.insureRecordList();
        //存入redis的缓存中
        insureRecordRedisDao.addRedisInsureRecordList(insureRecordList);
        return n;
    }
    // 根据id查
    public InsureRecordPo findById(InsureRecordPo insureRecord){
        return insureRecordDao.findById(insureRecord);
    }
    // 查询
    public List<InsureRecordPo> findInsureRecordList(){
        List<InsureRecordPo> insureRecordList = insureRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(insureRecordList.size() == 0){
            //从数据库查数据
            insureRecordList = insureRecordDao.insureRecordList();
            //存入redis的缓存中
            insureRecordRedisDao.addRedisInsureRecordList(insureRecordList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<InsureRecordPo> insureRecords = new ArrayList<InsureRecordPo>();
        for(InsureRecordPo insureRecordPo : insureRecordList) {
            InsureRecordPo item = new InsureRecordPo();
            BeanUtils.copyProperties(insureRecordPo, item);
            insureRecords.add(item);
        }
        return insureRecords ;
    }
}
