package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.CarPo;
import com.woniu.po.CarPo;
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
public class CarRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<CarPo> list(int pageIndex, String searchText, int pageSize) {
        List<CarPo> carList = new ArrayList<>();

        String key = "carList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                carList = objectMapper.readValue(dataStr, new TypeReference<List<CarPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return carList;
    }

    //将查询的数据存入redis中
    public void addRedisCarList(List<CarPo> carList, int pageIndex, String searchText,int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "carList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(carList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("carList*");
        redisTemplate.delete(keys);
    }
}
