package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.DriverPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/29/17:15
 * @Description:
 */
@Component
public class DriverRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<DriverPo> list() {

        List<DriverPo>  driverList= new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("driverList");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                driverList = objectMapper.readValue(dataStr, new TypeReference<List<DriverPo>>() { });
            }
        } catch (Exception ex) {  }
        return driverList;
    }

    //更新redis
    public void addRedisDriverList(List<DriverPo> driverList) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("driverList");
        try {
            String temp = objectMapper.writeValueAsString(driverList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
}
