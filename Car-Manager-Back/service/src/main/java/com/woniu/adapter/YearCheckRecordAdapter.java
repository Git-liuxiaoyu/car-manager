package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.YearCheckRecordDao;
import com.woniu.po.YearCheckRecordPo;
import com.woniu.redis.YearCheckRecordRedisDao;
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
public class YearCheckRecordAdapter {
    @Autowired
    private YearCheckRecordDao yearCheckRecordDao;
    @Autowired
    private YearCheckRecordRedisDao yearCheckRecordRedisDao;

    // 添加
    public int add(YearCheckRecordPo yearCheckRecordPo){
        // 向数据库里添加yearCheckRecord
        int n = yearCheckRecordDao.add(yearCheckRecordPo);
        // 从数据里查出所有yearCheckRecord
        List<YearCheckRecordPo> yearCheckRecordList = yearCheckRecordDao.yearCheckRecordList();
        //存入redis的缓存中
        yearCheckRecordRedisDao.addRedisYearCheckRecordList(yearCheckRecordList);
        return n;
    }
    // 修改
    public int update(YearCheckRecordPo yearCheckRecordPo){
        int n = yearCheckRecordDao.update(yearCheckRecordPo);
        // 从数据里查出所有yearCheckRecord
        List<YearCheckRecordPo> yearCheckRecordList = yearCheckRecordDao.yearCheckRecordList();
        //存入redis的缓存中
        yearCheckRecordRedisDao.addRedisYearCheckRecordList(yearCheckRecordList);
        return n;
    }
    // 删除
    public int delete(YearCheckRecordPo yearCheckRecord){
        int n = yearCheckRecordDao.delete(yearCheckRecord);
        // 从数据里查出所有yearCheckRecord
        List<YearCheckRecordPo> yearCheckRecordList = yearCheckRecordDao.yearCheckRecordList();
        //存入redis的缓存中
        yearCheckRecordRedisDao.addRedisYearCheckRecordList(yearCheckRecordList);
        return n;
    }
    // 根据id查
    public YearCheckRecordPo findById(YearCheckRecordPo yearCheckRecord){
        return yearCheckRecordDao.findById(yearCheckRecord);
    }
    // 查询
    public List<YearCheckRecordPo> findYearCheckRecordList(){
        List<YearCheckRecordPo> yearCheckRecordList = yearCheckRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(yearCheckRecordList.size() == 0){
            //从数据库查数据
            yearCheckRecordList = yearCheckRecordDao.yearCheckRecordList();
            //存入redis的缓存中
            yearCheckRecordRedisDao.addRedisYearCheckRecordList(yearCheckRecordList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<YearCheckRecordPo> yearCheckRecords = new ArrayList<YearCheckRecordPo>();
        for(YearCheckRecordPo yearCheckRecordPo : yearCheckRecordList) {
            YearCheckRecordPo item = new YearCheckRecordPo();
            BeanUtils.copyProperties(yearCheckRecordPo, item);
            yearCheckRecords.add(item);
        }
        return yearCheckRecords ;
    }
}
