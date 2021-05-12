package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.woniu.dao.GetRecordDao;
import com.woniu.dao.OppositeCompanyDao;
import com.woniu.domain.GetRecord;
import com.woniu.domain.OppositeCompany;
import com.woniu.po.GetRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.redis.GetRecordRedisDao;
import com.woniu.redis.OppositeCompanyRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class GetRecordAdapter {


    @Autowired
    private GetRecordRedisDao getRecordRedisDao;

    @Autowired
    private GetRecordDao GetRecordDao;

    /**
     * 查询列表
     * @return
     */
    public List<GetRecord> Lists(){
        List<GetRecordPo> getRecordPoList = getRecordRedisDao.list();
        ObjectMapper objectMapper = new ObjectMapper();
        if(getRecordPoList.size() == 0){
            //从数据库查数据
            getRecordPoList = GetRecordDao.list();
            //存入redis的缓存中
            getRecordRedisDao.addRedisUserList(getRecordPoList);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<GetRecord> records = new ArrayList<GetRecord>();
        for(GetRecordPo grpo : getRecordPoList) {
            GetRecord item = new GetRecord();
            BeanUtils.copyProperties(grpo, item);
            records.add(item);
        }
        return records;
    }


    /**
     * 添加取车记录
     * @return
     */
    public int add(GetRecordPo grpo){
        int i = GetRecordDao.add(grpo);
        if(i > 0){updateredis();}
        return i;
    }


    /**
     * 修改取车记录列表
     * @return
     */
    public int update(GetRecordPo grpo){
        int i = GetRecordDao.update(grpo);
        //修改成功
        if(i > 0){updateredis();}
        return i;
    }

    /**
     * 删除
     * @return
     */
    public int delete(GetRecordPo grpo){
        int i = GetRecordDao.delete(grpo);
        //删除成功
        if(i>0){ updateredis();}
        return i;
    }

    /**
     * 根据id查询
     * @param getRecordPo
     * @return
     */
    public GetRecordPo findbyid(GetRecordPo getRecordPo){

        return GetRecordDao.findbyid(getRecordPo);
    }



    /**
     * 更新redis
     */
    public void updateredis(){

        List<GetRecordPo> list = GetRecordDao.list();
        //修改redis的值
        getRecordRedisDao.addRedisUserList(list);
    }
}
