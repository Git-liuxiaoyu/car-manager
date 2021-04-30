package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.FeeRecordDao;
import com.woniu.domain.FeeRecord;
import com.woniu.domain.GetRecord;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;
import com.woniu.redis.FeeRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeeRecordAdapter {



    @Autowired
    private FeeRecordRedisDao feeRecordRedisDao;

    @Autowired
    private FeeRecordDao feeRecordDao;

    /**
     * 查询列表
     * @return
     */
    public List<FeeRecord> Lists(){
        List<FeeRecordPo> feeRecordList = feeRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(feeRecordList.size() == 0){
            //从数据库查数据
            feeRecordList = feeRecordDao.list();
            //存入redis的缓存中
            feeRecordRedisDao.addRedisUserList(feeRecordList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<FeeRecord> feeRecords = new ArrayList<FeeRecord>();
        for(FeeRecordPo frpo : feeRecordList) {
            FeeRecord item = new FeeRecord();
            BeanUtils.copyProperties(frpo, item);
            feeRecords.add(item);
        }
        return feeRecords;
    }


    /**
     * 添加取车记录
     * @return
     */
    public int add(FeeRecordPo fepo){
        int i = feeRecordDao.add(fepo);
        if(i > 0){updateredis();}
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(FeeRecordPo fepo){
        int i = feeRecordDao.update(fepo);
        //修改成功
        if(i > 0){updateredis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(FeeRecordPo fepo){
        int i = feeRecordDao.delete(fepo);
        //删除成功
        if(i>0){ updateredis();}
        return i;
    }

    /**
     * 根据id查询
     * @param
     * @return
     */
    public FeeRecordPo findbyid(FeeRecordPo fepo){

        return feeRecordDao.findbyid(fepo);
    }



    /**
     * 更新redis
     */
    public void updateredis(){

        List<FeeRecordPo> list = feeRecordDao.list();
        //修改redis的值
        feeRecordRedisDao.addRedisUserList(list);
    }
}
