package com.woniu.adapter;

import com.woniu.dao.YearCheckRecordDao;
import com.woniu.domain.YearCheckRecord;
import com.woniu.po.YearCheckRecordPo;
import com.woniu.redis.YearCheckRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YearCheckRecordAdapter {
    @Autowired
    private YearCheckRecordDao yearCheckRecordDao;
    @Autowired
    private YearCheckRecordRedisDao yearCheckRecordRedisDao;

    public List<YearCheckRecord> list(String searchText, int pageIndex,int pageSize) {
        //查询的list
        List<YearCheckRecordPo> yearCheckRecordList = yearCheckRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (yearCheckRecordList.size() == 0) {
            //从数据库查数据
            yearCheckRecordList = yearCheckRecordDao.list(searchText);
            //存入redis的缓存中
            yearCheckRecordRedisDao.addRedisYearCheckRecordList(yearCheckRecordList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<YearCheckRecord> yearCheckRecords = new ArrayList<>();
        for (YearCheckRecordPo e : yearCheckRecordList) {
            YearCheckRecord item = new YearCheckRecord();
            BeanUtils.copyProperties(e, item);
            yearCheckRecords.add(item);
        }
        return yearCheckRecords;
    }

    //查询总记录数
    public int count(String searchText) {
        return yearCheckRecordDao.count(searchText);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public void delete(Integer id) {
        yearCheckRecordDao.delete(id);
        yearCheckRecordRedisDao.updateRedis();
    }


    /**
     * 添加
     * @param yearCheckRecord
     * @return
     */
    public void add(YearCheckRecord yearCheckRecord){
        YearCheckRecordPo item = new YearCheckRecordPo();
        BeanUtils.copyProperties(yearCheckRecord, item);
        yearCheckRecordDao.add(item);
        yearCheckRecordRedisDao.updateRedis();
    }
    /**
     * 修改
     * @param yearCheckRecord
     * @return
     */
    public void update(YearCheckRecord yearCheckRecord){
        YearCheckRecordPo item = new YearCheckRecordPo();
        BeanUtils.copyProperties(yearCheckRecord, item);
        yearCheckRecordDao.update(item);
        yearCheckRecordRedisDao.updateRedis();
    }


}
