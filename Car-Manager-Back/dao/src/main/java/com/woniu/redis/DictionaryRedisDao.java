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

/**
 * 字典redis
 */
@Component
public class DictionaryRedisDao {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<DictionaryPo> list(int parentid) {

        List<DictionaryPo>  DictionaryList= new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("dictionlist"+parentid);
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
    public void addRedisDriverList(List<DictionaryPo> driverList,int parentid) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("dictionlist"+parentid);
        try {
            String temp = objectMapper.writeValueAsString(driverList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
    
}
