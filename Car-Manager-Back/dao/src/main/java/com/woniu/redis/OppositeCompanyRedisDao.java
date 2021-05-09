package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OppositeCompanyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class OppositeCompanyRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    public List<OppositeCompanyPo> list(int pageIndex, String searchText, int pageSize) {

        List<OppositeCompanyPo> Lists = new ArrayList<>();
        String key = "OppositeCompanyPos" + pageIndex+pageSize+searchText;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("取出"+key);
                Lists = objectMapper.readValue(dataStr, new TypeReference<List<OppositeCompanyPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return Lists;
    }



    //将查询的数据存入redis中
    public void addRedisUserList(List<OppositeCompanyPo> list, int pageIndex, String searchText, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "OppositeCompanyPos" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
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
        Set<String> keys=redisTemplate.keys("OppositeCompanyPos*");
        redisTemplate.delete(keys);
    }



//    //更新redis
//    public void addRedisUserList(List<OppositeCompanyPo> lists) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("OppositeCompanyPos");
//        try {
//            String temp = objectMapper.writeValueAsString(lists);
//            //3、然后把查到的结果存到redis里面
//            boundValueOps.set(temp);
//        } catch (Exception exception) {  }
//    }


}
