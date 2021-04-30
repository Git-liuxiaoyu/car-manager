package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.DepartureRecordPo;
import com.woniu.po.OilRecordPo;
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
public class OilRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<OilRecordPo> list() {

        List<OilRecordPo>  oilRecordPoList= new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("oilRecordList");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                oilRecordPoList = objectMapper.readValue(dataStr, new TypeReference<List<OilRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return oilRecordPoList;
    }

    //更新redis
    public void addRedisList(List<OilRecordPo> oilRecordPoList) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("oilRecordList");
        try {
            String temp = objectMapper.writeValueAsString(oilRecordPoList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }
}
