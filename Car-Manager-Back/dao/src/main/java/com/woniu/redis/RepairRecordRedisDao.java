package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.OilRecordPo;
import com.woniu.po.RepairRecordPo;
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
public class RepairRecordRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<RepairRecordPo> list(int pageIndex, String searchText, int pageSize) {
        List<RepairRecordPo> repairRecordList = new ArrayList<>();

        String key = "repairRecordList" + pageIndex + pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                repairRecordList = objectMapper.readValue(dataStr, new TypeReference<List<RepairRecordPo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return repairRecordList;
    }

    //将查询的数据存入redis中
    public void addRedisRepairRecordList(List<RepairRecordPo> repairRecordList, int pageIndex, String searchText, int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "repairRecordList" + pageIndex + pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(repairRecordList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis() {
        Set<String> keys = redisTemplate.keys("repairRecordList*");
        redisTemplate.delete(keys);
    }
}
