package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.GetRecordPo;
import com.woniu.po.OppositeCompanyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<GetRecordPo> list() {

        List<GetRecordPo> Lists = new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("GetRecordlist");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取GetRecordlist数据");
                Lists = objectMapper.readValue(dataStr, new TypeReference<List<GetRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return Lists;
    }

    //更新redis
    public void addRedisUserList(List<GetRecordPo> lists) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("GetRecordlist");
        try {
            String temp = objectMapper.writeValueAsString(lists);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }

}
