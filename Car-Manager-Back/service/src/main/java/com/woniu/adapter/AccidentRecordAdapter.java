package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.dao.AccidentRecordDao;

import com.woniu.domain.AccidentRecord;
import com.woniu.domain.Car;
import com.woniu.domain.AccidentRecord;
import com.woniu.po.AccidentRecordPo;
import com.woniu.po.AccidentRecordPo;
import com.woniu.redis.AccidentRecordRedisDao;
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
public class AccidentRecordAdapter {
    @Autowired
    private AccidentRecordDao accidentRecordDao;
    @Autowired
    private AccidentRecordRedisDao accidentRecordRedisDao;

    /**
     * 查询列表
     * @return
     */
    public List<AccidentRecord> List(String searchText, int pageIndex, int pageSize){
        List<AccidentRecordPo> accidentRecordPoList = accidentRecordRedisDao.list(pageIndex, searchText,pageSize);
        ObjectMapper objectMapper = new ObjectMapper();
        if(accidentRecordPoList.size() == 0){
            //从数据库查数据
            accidentRecordPoList = accidentRecordDao.list(searchText);
            //存入redis的缓存中
            accidentRecordRedisDao.addRedisAccidentRecordList(accidentRecordPoList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<AccidentRecord> accidentRecordList = new ArrayList<AccidentRecord>();
        for(AccidentRecordPo accidentRecordPo : accidentRecordPoList) {
            AccidentRecord item = new AccidentRecord();
            BeanUtils.copyProperties(accidentRecordPo, item);
//            Car car = new Car();
//            BeanUtils.copyProperties(frpo.getCarPo(), car);
//            item.setCar(car);
            accidentRecordList.add(item);
        }
        return accidentRecordList;
    }

    //查询总记录数
    public int count(String searchText) {
        return accidentRecordDao.count(searchText);
    }


    /**
     * 添加取车记录
     * @return
     */
    public int add(AccidentRecord accidentRecord){
        AccidentRecordPo accidentRecordPo = new AccidentRecordPo();
        BeanUtils.copyProperties(accidentRecord, accidentRecordPo);
        int i = accidentRecordDao.add(accidentRecordPo);
        if(i > 0){
            updateredis();
        }
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(AccidentRecord accidentRecord){
        AccidentRecordPo accidentRecordPo = new AccidentRecordPo();
        BeanUtils.copyProperties(accidentRecord, accidentRecordPo);


        int i = accidentRecordDao.update(accidentRecordPo);
        //修改成功
        if(i > 0){
            updateredis();
        }
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(Integer id){
//        AccidentRecordPo feeRecordPo = new AccidentRecordPo();
//        BeanUtils.copyProperties(feeRecord, feeRecordPo);
        int i = accidentRecordDao.delete(id);
        //删除成功
        if(i>0){
            updateredis();
        }
        return i;
    }


    /**
     * 更新redis
     */
    public void updateredis(){

        accidentRecordRedisDao.updateRedis();
    }
}
