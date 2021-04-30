package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.RepairRecordDao;
import com.woniu.dao.RepairRecordDao;
import com.woniu.domain.RepairRecord;
import com.woniu.po.RepairRecordPo;
import com.woniu.redis.RepairRecordRedisDao;
import com.woniu.redis.RepairRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepairRecordAdapter {
    @Autowired
    private RepairRecordDao repairRecordDao;

    @Autowired
    private RepairRecordRedisDao repairRecordRedisDao;

    public List<RepairRecord> findLists(){
        List<RepairRecordPo> repairRecordPoList = repairRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(repairRecordPoList.size() == 0){
            //从数据库查数据
            repairRecordPoList = repairRecordDao.findList();
//            driverList = list
            //存入redis的缓存中
            repairRecordRedisDao.addRedisList(repairRecordPoList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<RepairRecord> repairRecords = new ArrayList<RepairRecord>();
        for(RepairRecordPo repairRecordPo : repairRecordPoList) {
            RepairRecord item = new RepairRecord();
            BeanUtils.copyProperties(repairRecordPo, item);
            repairRecords.add(item);
        }
        return repairRecords;
    }


    /**
     * 添加驾驶员档案
     * @param oilRecord
     * @return
     */
    public int add(RepairRecord oilRecord){

        RepairRecordPo item = new RepairRecordPo();
        BeanUtils.copyProperties(oilRecord, item);
        int i = repairRecordDao.add(item);
        if (i>0){

            List<RepairRecordPo> repairRecordPos = repairRecordDao.findList();
            //存入redis的缓存中
            repairRecordRedisDao.addRedisList(repairRecordPos);
        }
        return i;
    }
    /**
     * 修改加油信息
     * @param oilRecord
     * @return
     */
    public int update(RepairRecord oilRecord){

        RepairRecordPo item = new RepairRecordPo();
        BeanUtils.copyProperties(oilRecord, item);
        int i = repairRecordDao.update(item);
        if (i>0){
            List<RepairRecordPo> repairRecordPos = repairRecordDao.findList();
            //存入redis的缓存中
            repairRecordRedisDao.addRedisList(repairRecordPos);
        }
        return i;
    }

    /**
     * 删除加油信息
     * @param id
     * @return
     */
    public int delete(Integer id){

//        RepairRecordPo item = new RepairRecordPo();
//        BeanUtils.copyProperties(oilRecord, item);
        int i = repairRecordDao.delete(id);
        if (i>0){
            List<RepairRecordPo> repairRecordPos = repairRecordDao.findList();
            //存入redis的缓存中
            repairRecordRedisDao.addRedisList(repairRecordPos);
        }
        return i;
    }


}
