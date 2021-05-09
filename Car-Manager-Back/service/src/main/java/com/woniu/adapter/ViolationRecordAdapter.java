package com.woniu.adapter;

import com.woniu.dao.ViolationRecordDao;
import com.woniu.domain.ViolationRecord;
import com.woniu.po.ViolationRecordPo;
import com.woniu.redis.ViolationRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViolationRecordAdapter {
    @Autowired
    private ViolationRecordDao violationRecordDao;
    @Autowired
    private ViolationRecordRedisDao violationRecordRedisDao;

    public List<ViolationRecord> list(String searchText, int pageIndex,int pageSize) {
        //查询的list
        List<ViolationRecordPo> violationRecordList = violationRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (violationRecordList.size() == 0) {
            //从数据库查数据
            violationRecordList = violationRecordDao.list(searchText);
            //存入redis的缓存中
            violationRecordRedisDao.addRedisViolationRecordList(violationRecordList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<ViolationRecord> violationRecords = new ArrayList<>();
        for (ViolationRecordPo e : violationRecordList) {
            ViolationRecord item = new ViolationRecord();
            BeanUtils.copyProperties(e, item);
            violationRecords.add(item);
        }
        return violationRecords;
    }

    //查询总记录数
    public int count(String searchText) {
        return violationRecordDao.count(searchText);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public void delete(Integer id) {
        violationRecordDao.delete(id);
        violationRecordRedisDao.updateRedis();
    }


    /**
     * 添加
     * @param violationRecord
     * @return
     */
    public void add(ViolationRecord violationRecord){
        ViolationRecordPo item = new ViolationRecordPo();
        BeanUtils.copyProperties(violationRecord, item);
        violationRecordDao.add(item);
        violationRecordRedisDao.updateRedis();
    }
    /**
     * 修改
     * @param violationRecord
     * @return
     */
    public void update(ViolationRecord violationRecord){
        ViolationRecordPo item = new ViolationRecordPo();
        BeanUtils.copyProperties(violationRecord, item);
        violationRecordDao.update(item);
        violationRecordRedisDao.updateRedis();
    }


}
