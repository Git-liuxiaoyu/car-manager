package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.DepartureRecordPo;
import com.woniu.po.ReturnRecordPo;
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
 * @Auther: khx
 * @Date: 2021/04/29/17:15
 * @Description:
 */

@Component
public class ReturnRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<ReturnRecordPo> list() {

        List<ReturnRecordPo>  returnRecordPoList= new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("returnRecordList");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                returnRecordPoList = objectMapper.readValue(dataStr, new TypeReference<List<ReturnRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return returnRecordPoList;
    }

    //更新redis
    public void addRedisList(List<ReturnRecordPo> returnRecordPoList) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("returnRecordList");
        try {
            String temp = objectMapper.writeValueAsString(returnRecordPoList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
}
