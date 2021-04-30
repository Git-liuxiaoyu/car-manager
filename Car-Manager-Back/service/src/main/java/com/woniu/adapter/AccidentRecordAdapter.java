package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.dao.AccidentRecordDao;

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

    // 添加
    public int add(AccidentRecordPo accidentRecordPo){
        // 向数据库里添加accidentRecord
        int n = accidentRecordDao.add(accidentRecordPo);
        // 从数据里查出所有accidentRecord
        List<AccidentRecordPo> accidentRecordList = accidentRecordDao.accidentRecordList(accidentRecordPo);
        //存入redis的缓存中
        accidentRecordRedisDao.addRedisAccidentRecordList(accidentRecordList);
        return n;
    }
    // 修改
    public int update(AccidentRecordPo accidentRecordPo){
        int n = accidentRecordDao.update(accidentRecordPo);
        // 从数据里查出所有accidentRecord
        List<AccidentRecordPo> accidentRecordList = accidentRecordDao.accidentRecordList(accidentRecordPo);
        //存入redis的缓存中
        accidentRecordRedisDao.addRedisAccidentRecordList(accidentRecordList);
        return n;
    }
    // 删除
    public int delete(AccidentRecordPo accidentRecord){
        int n = accidentRecordDao.delete(accidentRecord);
        // 从数据里查出所有accidentRecord
        List<AccidentRecordPo> accidentRecordList = accidentRecordDao.accidentRecordList(accidentRecord);
        //存入redis的缓存中
        accidentRecordRedisDao.addRedisAccidentRecordList(accidentRecordList);
        return n;
    }
    // 根据id查
    public AccidentRecordPo findById(AccidentRecordPo accidentRecord){
        return accidentRecordDao.findById(accidentRecord);
    }
    // 查询
    public List<AccidentRecordPo> findAccidentRecordList(AccidentRecordPo accidentRecord){
        List<AccidentRecordPo> accidentRecordList = accidentRecordRedisDao.list(accidentRecord);
        ObjectMapper objectMapper = new ObjectMapper();
        if(accidentRecordList.size() == 0){
            //从数据库查数据
            accidentRecordList = accidentRecordDao.accidentRecordList(accidentRecord);
            //存入redis的缓存中
            accidentRecordRedisDao.addRedisAccidentRecordList(accidentRecordList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<AccidentRecordPo> accidentRecords = new ArrayList<AccidentRecordPo>();
        for(AccidentRecordPo accidentRecordPo : accidentRecordList) {
            AccidentRecordPo item = new AccidentRecordPo();
            BeanUtils.copyProperties(accidentRecordPo, item);
            accidentRecords.add(item);
        }
        return accidentRecords ;
    }
}
