package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.AccidentRecordPo;
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
public class AccidentRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    public List<AccidentRecordPo> list(AccidentRecordPo accidentRecordPo) {

        List<AccidentRecordPo> accidentRecordList = new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("accidentRecordlist");
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取accidentRecord数据");
                accidentRecordList = objectMapper.readValue(dataStr, new TypeReference<List<AccidentRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return accidentRecordList;
    }

    //更新redis
    public void addRedisAccidentRecordList(List<AccidentRecordPo> list) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("accidentRecordlist");
        try {
            String temp = objectMapper.writeValueAsString(list);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
}
