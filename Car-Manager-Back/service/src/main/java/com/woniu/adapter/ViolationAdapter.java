package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.dao.ViolationDao;
import com.woniu.domain.ViolationRecord;
import com.woniu.po.ViolationRecordPo;
import com.woniu.redis.KeeRecordRedisDao;
import com.woniu.redis.ViolationRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ViolationAdapter {



    @Autowired
    private ViolationRecordRedisDao violationRecordRedisDao;

    @Autowired
    private ViolationDao violationDao;

    /**
     * 查询列表
     * @return
     */
    public List<ViolationRecord> Lists(){
        List<ViolationRecordPo> violationlist = violationRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(violationlist.size() == 0){
            //从数据库查数据
            violationlist = violationDao.list();
            //存入redis的缓存中
            violationRecordRedisDao.addRedisUserList(violationlist);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<ViolationRecord> records = new ArrayList<ViolationRecord>();
        for(ViolationRecordPo grpo : violationlist) {
            ViolationRecord item = new ViolationRecord();
            BeanUtils.copyProperties(grpo, item);
            records.add(item);
        }
        return records;
    }


    /**
     * 添加取车记录
     * @return
     */
    public int add(ViolationRecordPo grpo){
        int i = violationDao.add(grpo);
        if(i > 0){updateredis();}
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(ViolationRecordPo grpo){
        int i = violationDao.update(grpo);
        //修改成功
        if(i > 0){updateredis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(ViolationRecordPo grpo){
        int i = violationDao.delete(grpo);
        //删除成功
        if(i>0){ updateredis();}
        return i;
    }

    /**
     * 根据id查询
     * @param krpo
     * @return
     */
    public ViolationRecordPo findbyid(ViolationRecordPo krpo){

        return violationDao.findbyid(krpo);
    }



    /**
     * 更新redis
     */
    public void updateredis(){

        List<ViolationRecordPo> list = violationDao.list();
        //修改redis的值
        violationRecordRedisDao.addRedisUserList(list);
    }

}
