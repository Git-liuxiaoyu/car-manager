package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:15
 */
@Component
public class CarRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
//    // 增加
//    public int add(Car car){
//        int n = 0;
//        List<Car> carList = new ArrayList<>();
//        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("carlist");
//        String dataStr = boundValueOps.get();
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            carList = objectMapper.readValue(dataStr, new TypeReference<List<Car>>() { });
//            carList.add(car);
//            n = 1;
//        }catch (Exception e){
//
//        }
//        return n;
//    }
    public List<Car> list() {

        List<Car> carList = new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("carlist");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取car数据");
                carList = objectMapper.readValue(dataStr, new TypeReference<List<Car>>() { });
            }
        } catch (Exception ex) {  }
        return carList;
    }

    //更新redis
    public void addRedisCarList(List<Car> list) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("carlist");
        try {
            String temp = objectMapper.writeValueAsString(list);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
}
