package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.DictionaryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 字典redis
 */
@Component
public class DictionaryRedisDao {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<DictionaryPo> list(String searchText, int pageIndex, int pageSize) {
        List<DictionaryPo>  DictionaryList= new ArrayList<>();
        String key = "dictionlist" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                DictionaryList = objectMapper.readValue(dataStr, new TypeReference<List<DictionaryPo>>() { });
            }
        } catch (Exception ex) {  }
        return DictionaryList;
    }

    //更新redis
    public void addRedisDriverList(List<DictionaryPo> driverList,String searchText, int pageIndex, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "dictionlist" + pageIndex+pageSize;
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
        Set<String> keys=redisTemplate.keys("dictionlist*");
        redisTemplate.delete(keys);
    }
}
