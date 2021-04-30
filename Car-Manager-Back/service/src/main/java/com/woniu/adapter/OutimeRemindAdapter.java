package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.OutimeRemindDao;
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
        // 从数据里查出所有outimeRemind
        List<OutimeRemindPo> outimeRemindList = outimeRemindDao.outimeRemindList();
        //存入redis的缓存中
        outimeRemindRedisDao.addRedisOutimeRemindList(outimeRemindList);
        return n;
    }
    // 修改
    public int update(OutimeRemindPo outimeRemindPo){
        int n = outimeRemindDao.update(outimeRemindPo);
        // 从数据里查出所有outimeRemind
        List<OutimeRemindPo> outimeRemindList = outimeRemindDao.outimeRemindList();
        //存入redis的缓存中
        outimeRemindRedisDao.addRedisOutimeRemindList(outimeRemindList);
        return n;
    }
    // 删除
    public int delete(OutimeRemindPo outimeRemind){
        int n = outimeRemindDao.delete(outimeRemind);
        // 从数据里查出所有outimeRemind
        List<OutimeRemindPo> outimeRemindList = outimeRemindDao.outimeRemindList();
        //存入redis的缓存中
        outimeRemindRedisDao.addRedisOutimeRemindList(outimeRemindList);
        return n;
    }
    // 根据id查
    public OutimeRemindPo findById(OutimeRemindPo outimeRemind){
        return outimeRemindDao.findById(outimeRemind);
    }
    // 查询
    public List<OutimeRemindPo> findOutimeRemindList(){
        List<OutimeRemindPo> outimeRemindList = outimeRemindRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(outimeRemindList.size() == 0){
            //从数据库查数据
            outimeRemindList = outimeRemindDao.outimeRemindList();
            //存入redis的缓存中
            outimeRemindRedisDao.addRedisOutimeRemindList(outimeRemindList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<OutimeRemindPo> outimeReminds = new ArrayList<OutimeRemindPo>();
        for(OutimeRemindPo outimeRemindPo : outimeRemindList) {
            OutimeRemindPo item = new OutimeRemindPo();
            BeanUtils.copyProperties(outimeRemindPo, item);
            outimeReminds.add(item);
        }
        return outimeReminds ;
    }
}
