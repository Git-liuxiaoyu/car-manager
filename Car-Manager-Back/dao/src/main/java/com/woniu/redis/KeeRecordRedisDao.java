package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.KeepRecordPo;
import com.woniu.po.KeepRecordPo;
import com.woniu.po.KeepRecordPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 保养记录
 */
@Component
public class KeeRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<KeepRecordPo> list(int pageIndex, String searchText, int pageSize) {

        List<KeepRecordPo> KeepRecordPoList = new ArrayList<>();
        String key = "KeepRecordPoList" + pageIndex+pageSize+searchText;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("取出"+key);
                KeepRecordPoList = objectMapper.readValue(dataStr, new TypeReference<List<KeepRecordPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return KeepRecordPoList;
    }


    //将查询的数据存入redis中
    public void addRedisList(List<KeepRecordPo> KeepRecordPo, int pageIndex, String searchText, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "KeepRecordPoList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(KeepRecordPo);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("KeepRecordPoList*");
        redisTemplate.delete(keys);
    }


//    //更新redis
//    public void addRedisUserList(List<KeepRecordPo> lists) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("keerecordlist");
//        try {
//            String temp = objectMapper.writeValueAsString(lists);
//            //3、然后把查到的结果存到redis里面
//            boundValueOps.set(temp);
//        } catch (Exception exception) {  }
//    }
}
