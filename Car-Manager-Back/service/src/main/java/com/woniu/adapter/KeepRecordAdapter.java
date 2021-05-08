package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.KeepRecordDao;
import com.woniu.dao.KeepRecordDao;
import com.woniu.domain.KeepRecord;
import com.woniu.domain.OilRecord;
import com.woniu.po.KeepRecordPo;

import com.woniu.po.OilRecordPo;
import com.woniu.redis.KeeRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeepRecordAdapter {



    @Autowired
    private KeeRecordRedisDao keepRecordRedisDao;

    @Autowired
    private KeepRecordDao keepRecordDao;

    //分页查询
    public List<KeepRecord> Lists(String searchText, int pageIndex, int pageSize) {
        //查询的list
        List<KeepRecordPo> keepRecordPoList = keepRecordRedisDao.list(pageIndex, searchText,pageSize);

        if (keepRecordPoList.size() == 0) {
            //从数据库查数据
            keepRecordPoList = keepRecordDao.findList(searchText);
            //存入redis的缓存中
            keepRecordRedisDao.addRedisList(keepRecordPoList, pageIndex, searchText,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<KeepRecord> keepRecords = new ArrayList<>();
        for (KeepRecordPo e : keepRecordPoList) {
            KeepRecord item = new KeepRecord();
            BeanUtils.copyProperties(e, item);
            keepRecords.add(item);
        }
        return keepRecords;
    }

    //查询总记录数
    public int count(String searchText) {
        return keepRecordDao.count(searchText);
    }





    /**
     * 添加取车记录
     * @return
     */
    public int add(KeepRecordPo grpo){
        int i = keepRecordDao.add(grpo);
        if(i > 0){keepRecordRedisDao.updateRedis();}
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(KeepRecordPo grpo){
        int i = keepRecordDao.update(grpo);
        //修改成功
        if(i > 0){keepRecordRedisDao.updateRedis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(KeepRecordPo grpo){
        int i = keepRecordDao.delete(grpo);
        //删除成功
        if(i>0){keepRecordRedisDao.updateRedis();}
        return i;
    }

    /**
     * 根据id查询
     * @param krpo
     * @return
     */
    public KeepRecordPo findbyid(KeepRecordPo krpo){

        return keepRecordDao.findbyid(krpo);
    }


    
}
