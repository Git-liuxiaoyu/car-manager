package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.KeepRecordDao;
import com.woniu.dao.KeepRecordDao;
import com.woniu.domain.KeepRecord;
import com.woniu.po.KeepRecordPo;

import com.woniu.redis.KeeRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeepRecordAdapter {



    @Autowired
    private KeeRecordRedisDao keepRecordRedisDao;

    @Autowired
    private KeepRecordDao keepRecordDao;

    /**
     * 查询列表
     * @return
     */
    public List<KeepRecord> Lists(){
        List<KeepRecordPo> keepRecordPoList = keepRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(keepRecordPoList.size() == 0){
            //从数据库查数据
            keepRecordPoList = keepRecordDao.list();
            //存入redis的缓存中
            keepRecordRedisDao.addRedisUserList(keepRecordPoList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<KeepRecord> records = new ArrayList<KeepRecord>();
        for(KeepRecordPo grpo : keepRecordPoList) {
            KeepRecord item = new KeepRecord();
            BeanUtils.copyProperties(grpo, item);
            records.add(item);
        }
        return records;
    }


    /**
     * 添加取车记录
     * @return
     */
    public int add(KeepRecordPo grpo){
        int i = keepRecordDao.add(grpo);
        if(i > 0){updateredis();}
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(KeepRecordPo grpo){
        int i = keepRecordDao.update(grpo);
        //修改成功
        if(i > 0){updateredis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(KeepRecordPo grpo){
        int i = keepRecordDao.delete(grpo);
        //删除成功
        if(i>0){ updateredis();}
        return i;
    }

    /**
     * 根据id查询
     * @param krpo
     * @return
     */
    public KeepRecordPo findbyid(KeepRecordPo krpo){

        return keepRecordDao.findbyid(krpo);
    }



    /**
     * 更新redis
     */
    public void updateredis(){

        List<KeepRecordPo> list = keepRecordDao.list();
        //修改redis的值
        keepRecordRedisDao.addRedisUserList(list);
    }
    
}
