package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DepartureRecordDao;
import com.woniu.dao.ReturnRecordDao;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.po.DepartureRecordPo;
import com.woniu.po.ReturnRecordPo;
import com.woniu.redis.DepartureRecordRedisDao;
import com.woniu.redis.ReturnRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReturnRecordAdapter {
    @Autowired
    private ReturnRecordDao returnRecordDao;

    @Autowired
    private ReturnRecordRedisDao returnRecordRedisDao;

//    public List<DepartureRecord> findDriverLists(){
//        List<DepartureRecordPo> departureRecordPoList = departureRecordRedisDao.list();
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(departureRecordPoList.size() == 0){
//            //从数据库查数据
//            departureRecordPoList = departureRecordDao.findList();
////            driverList = list
//            //存入redis的缓存中
//            departureRecordRedisDao.addRedisDriverList(departureRecordPoList);
//        }
//        //把dao的 RoleList RolePo --- 转成  List<Role>
//        List<DepartureRecord> departureRecords = new ArrayList<DepartureRecord>();
//        for(DepartureRecordPo departureRecordPo : departureRecordPoList) {
//            DepartureRecord item = new DepartureRecord();
//            BeanUtils.copyProperties(departureRecordPo, item);
//            departureRecords.add(item);
//        }
//        return departureRecords;
//    }


    /**
     * 添加驾驶员档案
     * @param returnRecord
     * @return
     */
    public int add(ReturnRecord returnRecord){

        ReturnRecordPo item = new ReturnRecordPo();
        BeanUtils.copyProperties(returnRecord, item);
        int i = returnRecordDao.add(item);
        if (i>0){

            List<ReturnRecordPo> returnRecordPos = returnRecordDao.findList();
            //存入redis的缓存中
            returnRecordRedisDao.addRedisList(returnRecordPos);
        }
        return i;
    }


}
