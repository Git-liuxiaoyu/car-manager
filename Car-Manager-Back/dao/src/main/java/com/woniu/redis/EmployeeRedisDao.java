package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.EmployeePo;
import com.woniu.po.RolePo;
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
public class EmployeeRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<EmployeePo> list(int pageIndex, String searchText,int pageSize) {
        List<EmployeePo> employeeList = new ArrayList<>();

        String key = "employeeList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }

        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        String dataStr = boundValueOps.get();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取数据");
                employeeList = objectMapper.readValue(dataStr, new TypeReference<List<EmployeePo>>() {
                });
            }
        } catch (Exception ex) {
        }
        return employeeList;
    }

    //将查询的数据存入redis中
    public void addRedisEmployeeList(List<EmployeePo> employeeList, int pageIndex, String searchText,int pageSize) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "employeeList" + pageIndex+pageSize;
        if (searchText != null && !searchText.equals("")) {
            key += searchText;
        }
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps(key);
        try {
            String temp = objectMapper.writeValueAsString(employeeList);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {
        }
    }

    //清除redis的数据
    public void updateRedis(){
        Set<String> keys=redisTemplate.keys("employeeList*");
        redisTemplate.delete(keys);
    }
}
