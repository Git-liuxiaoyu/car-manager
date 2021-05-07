package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FeeRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<FeeRecordPo> list(int pageIndex, String searchText,int pageSize) {

        List<FeeRecordPo> Lists = new ArrayList<>();
        String key = "feerecordlist" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取GetRecordlist数据");
                Lists = objectMapper.readValue(dataStr, new TypeReference<List<FeeRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return Lists;
    }

    //更新redis
    public void addRedisUserList(List<FeeRecordPo> lists, int pageIndex, String searchText,int pageSize) {

        ObjectMapper objectMapper = new ObjectMapper();
        String key = "feerecordlist" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(lists);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("feerecordlist*");
        Long delete = redisTemplate.delete(keys);
        System.out.println(delete);
    }

}
