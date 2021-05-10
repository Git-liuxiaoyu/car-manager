package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:15
 */
@Component
public class OutimeRemindRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<OutimeRemindPo> list(int pageIndex, String nexttime,String thistime, int pageSize) {
        List<OutimeRemindPo> outimeRemindList = new ArrayList<>();
        String key = "outimeRemindlist" + pageIndex+pageSize;
        if (nexttime != null && !nexttime.equals("")) {
            key += nexttime+thistime;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("取出"+key);
                outimeRemindList = objectMapper.readValue(dataStr, new TypeReference<List<OutimeRemindPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return outimeRemindList;
    }


    //将查询的数据存入redis中
    public void addRedisOutimeRemindList(List<OutimeRemindPo> list, int pageIndex, String nexttime,String thistime, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "outimeRemindlist" + pageIndex+pageSize;
        if (nexttime != null && !nexttime.equals("")) {
            key += nexttime+=thistime;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(list);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("outimeRemindlist*");
        redisTemplate.delete(keys);
    }








//    //更新redis
//    public void addRedisOutimeRemindList(List<OutimeRemindPo> list) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("outimeRemindlist");
//        try {
//            String temp = objectMapper.writeValueAsString(list);
//            //3、然后把查到的结果存到redis里面
//            boundValueOps.set(temp);
//        } catch (Exception exception) {  }
//    }
}
