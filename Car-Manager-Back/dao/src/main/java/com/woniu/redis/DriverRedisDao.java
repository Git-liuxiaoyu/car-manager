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
import java.util.Set;

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

    public List<DriverPo> list(int pageIndex, String searchText,int pageSize) {

        List<DriverPo>  driverList= new ArrayList<>();

        String key = "driverList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
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
    public void addRedisDriverList(List<DriverPo> driverList, int pageIndex, String searchText,int pageSize) {

        ObjectMapper objectMapper = new ObjectMapper();

        String key = "driverList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(driverList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("driverList*");
        redisTemplate.delete(keys);
    }
}
