package com.woniu.adapter;

import com.woniu.dao.RepairRecordDao;
import com.woniu.domain.RepairRecord;
import com.woniu.po.RepairRecordPo;
import com.woniu.redis.CarRedisDao;
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

    @Autowired
    private CarRedisDao carRedisDao;

    public List<RepairRecord> list(String searchText, int pageIndex,int pageSize) {
        //查询的list
        List<RepairRecordPo> repairRecordList = repairRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (repairRecordList.size() == 0) {
            //从数据库查数据
            repairRecordList = repairRecordDao.list(searchText);
            //存入redis的缓存中
            repairRecordRedisDao.addRedisRepairRecordList(repairRecordList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<RepairRecord> repairRecords = new ArrayList<>();
        for (RepairRecordPo e : repairRecordList) {
            RepairRecord item = new RepairRecord();
            BeanUtils.copyProperties(e, item);
            repairRecords.add(item);
        }
        return repairRecords;
    }

    //查询总记录数
    public int count(String searchText) {
        return repairRecordDao.count(searchText);
    }

    /**
     * 删除加油信息
     * @param id
     * @return
     */
    public void delete(Integer id) {
        repairRecordDao.delete(id);
        repairRecordRedisDao.updateRedis();
    }


    /**
     * 添加驾驶员档案
     * @param repairRecord
     * @return
     */
    public void add(RepairRecord repairRecord){
        RepairRecordPo item = new RepairRecordPo();
        BeanUtils.copyProperties(repairRecord, item);
        repairRecordDao.add(item);
        repairRecordRedisDao.updateRedis();
        carRedisDao.updateRedis();//刷新车辆redis，车辆状态修改
    }
    /**
     * 修改加油信息
     * @param repairRecord
     * @return
     */
    public void update(RepairRecord repairRecord){

        RepairRecordPo item = new RepairRecordPo();
        BeanUtils.copyProperties(repairRecord, item);
        repairRecordDao.update(item);
        repairRecordRedisDao.updateRedis();//刷新redis
        carRedisDao.updateRedis();//刷新车辆redis，车辆状态修改

    }


}
