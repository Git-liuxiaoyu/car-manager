package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.ViolationRecordPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: khx
 * @Date: 2021/04/29/17:15
 * @Description:
 */

@Component
public class ViolationRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<ViolationRecordPo> list(int pageIndex, String searchText,int pageSize) {
        List<ViolationRecordPo> violationRecordList = new ArrayList<>();

        String key = "violationRecordList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                violationRecordList = objectMapper.readValue(dataStr, new TypeReference<List<ViolationRecordPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return violationRecordList;
    }

    //将查询的数据存入redis中
    public void addRedisViolationRecordList(List<ViolationRecordPo> violationRecordList, int pageIndex, String searchText,int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "violationRecordList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(violationRecordList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("violationRecordList*");
        redisTemplate.delete(keys);
    }
}
