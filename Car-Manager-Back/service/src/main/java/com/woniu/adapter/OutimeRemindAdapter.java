package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.OutimeRemindDao;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.redis.OutimeRemindRedisDao;
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
public class OutimeRemindAdapter {
    @Autowired
    private OutimeRemindDao outimeRemindDao;
    @Autowired
    private OutimeRemindRedisDao outimeRemindRedisDao;

    // 添加
    public int add(OutimeRemindPo outimeRemindPo){
        // 向数据库里添加outimeRemind
        int n = outimeRemindDao.add(outimeRemindPo);
        //清空redis
        outimeRemindRedisDao.updateRedis();
        return n;
    }
    // 修改
    public int update(OutimeRemindPo outimeRemindPo){
        int n = outimeRemindDao.update(outimeRemindPo);
        //清空redis
        outimeRemindRedisDao.updateRedis();
        return n;
    }
    // 删除
    public int delete(OutimeRemindPo outimeRemind){
        int n = outimeRemindDao.delete(outimeRemind);
        //清空redis
        outimeRemindRedisDao.updateRedis();
        return n;
    }
    // 根据id查
    public OutimeRemindPo findById(OutimeRemindPo outimeRemind){
        return outimeRemindDao.findById(outimeRemind);
    }


    //分页查询
    public List<OutimeRemindPo> findOutimeRemindList(String nexttime,String thistime, int pageIndex, int pageSize) {
        //查询的list
        List<OutimeRemindPo> outimeRemindList = outimeRemindRedisDao.list(pageIndex, nexttime,thistime,pageSize);

        if (outimeRemindList.size() == 0) {
            //从数据库查数据
            outimeRemindList = outimeRemindDao.outimeRemindList(nexttime,thistime);
            //存入redis的缓存中
            outimeRemindRedisDao.addRedisOutimeRemindList(outimeRemindList, pageIndex, nexttime,thistime,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<RolePo>
        List<OutimeRemindPo> outimeReminds = new ArrayList<>();
        for (OutimeRemindPo outimeRemindPo : outimeRemindList) {
            OutimeRemindPo item = new OutimeRemindPo();
            BeanUtils.copyProperties(outimeRemindPo, item);
            outimeReminds.add(item);
        }
        //清空redis
        outimeRemindRedisDao.updateRedis();
        return outimeReminds;
    }

    //查询总记录数
    public int count(String nexttime,String thistime) {
        return outimeRemindDao.count(nexttime,thistime);
    }





}
