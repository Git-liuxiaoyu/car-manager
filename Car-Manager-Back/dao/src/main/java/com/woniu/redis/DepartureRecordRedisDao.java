package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.DepartureRecordPo;
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
 * @Auther: khx
 * @Date: 2021/04/29/17:15
 * @Description:
 */

@Component
public class DepartureRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<DepartureRecordPo> list(String searchText, int pageIndex, int pageSize) {
        String key = "departureRecordList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        List<DepartureRecordPo>  departureRecordPoList= new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                departureRecordPoList = objectMapper.readValue(dataStr, new TypeReference<List<DepartureRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return departureRecordPoList;
    }

    //更新redis
    public void addRedisDriverList(List<DepartureRecordPo> departureRecordList,String searchText, int pageIndex, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "departureRecordList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(departureRecordList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("departureRecordList*");
        redisTemplate.delete(keys);
    }
}
