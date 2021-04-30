package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.OilRecordDao;
import com.woniu.dao.OilRecordDao;
import com.woniu.domain.OilRecord;
import com.woniu.po.OilRecordPo;
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

    public List<OilRecord> findLists(){
        List<OilRecordPo> oilRecordPoList = oilRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(oilRecordPoList.size() == 0){
            //从数据库查数据
            oilRecordPoList = oilRecordDao.findList();
//            driverList = list
            //存入redis的缓存中
            oilRecordRedisDao.addRedisList(oilRecordPoList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<OilRecord> oilRecords = new ArrayList<OilRecord>();
        for(OilRecordPo oilRecordPo : oilRecordPoList) {
            OilRecord item = new OilRecord();
            BeanUtils.copyProperties(oilRecordPo, item);
            oilRecords.add(item);
        }
        return oilRecords;
    }


    /**
     * 添加驾驶员档案
     * @param oilRecord
     * @return
     */
    public int add(OilRecord oilRecord){

        OilRecordPo item = new OilRecordPo();
        BeanUtils.copyProperties(oilRecord, item);
        int i = oilRecordDao.add(item);
        if (i>0){

            List<OilRecordPo> oilRecordPos = oilRecordDao.findList();
            //存入redis的缓存中
            oilRecordRedisDao.addRedisList(oilRecordPos);
        }
        return i;
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
            List<OilRecordPo> oilRecordPos = oilRecordDao.findList();
            //存入redis的缓存中
            oilRecordRedisDao.addRedisList(oilRecordPos);
        }
        return i;
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
            List<OilRecordPo> oilRecordPos = oilRecordDao.findList();
            //存入redis的缓存中
            oilRecordRedisDao.addRedisList(oilRecordPos);
        }
        return i;
    }


}
