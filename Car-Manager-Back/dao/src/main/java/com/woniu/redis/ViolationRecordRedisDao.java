package com.woniu.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.po.ViolationRecordPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViolationRecordRedisDao {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<ViolationRecordPo> list() {

        List<ViolationRecordPo> Lists = new ArrayList<>();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("violationRecordlist");
        String dataStr = boundValueOps.get();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(dataStr)) {
                System.out.println("从redis缓存中取violationRecordlist数据");
                Lists = objectMapper.readValue(dataStr, new TypeReference<List<ViolationRecordPo>>() { });
            }
        } catch (Exception ex) {  }
        return Lists;
    }

    //更新redis
    public void addRedisUserList(List<ViolationRecordPo> lists) {

        ObjectMapper objectMapper = new ObjectMapper();
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("violationRecordlist");
        try {
            String temp = objectMapper.writeValueAsString(lists);
            //3、然后把查到的结果存到redis里面
            boundValueOps.set(temp);
        } catch (Exception exception) {  }
    }



//    /**
//     * 存放单个hash缓存
//     * @param key 键
//     * @param hkey 键
//     * @param value 值
//     * @return
//     */
//    public static boolean hput(String key, String hkey, Object value) {
//        try {
//            redisTemplate.opsForHash().put(key, hkey, value);
//            log.debug("hput {} = {}", key+hkey, value);
//            return true;
//        } catch (Exception e) {
//            log.warn("hput {} = {}", key+hkey, value, e);
//        }
//        return false;
//    }
//
//    /**
//     * 分页存取数据
//     * @param key  hash存取的key
//     * @param hkey hash存取的hkey
//     * @param score 指定字段排序
//     * @param value
//     * @return
//     */
//    public static boolean setPage(String key, String hkey, double score, String value){
//        boolean result = false;
//        try {
//            redisTemplate.opsForZSet().add(key+":page", hkey, score);
//            result = hput(key, hkey, value);
//            log.debug("setPage {}", key);
//        } catch (Exception e) {
//            log.warn("setPage {}", key, e);
//        }
//        return result;
//    }
//
//    /**
//     * 分页取出 hash中hkey值
//     * @param key
//     * @param offset
//     * @param count
//     * @return
//     */
//    public static Set<String> getPage(String key, int offset, int count){
//        Set<String> result = null;
//        try {
//            result = redisTemplate.opsForZSet().rangeByScore(key+":page", 1, 100000, (offset-1)*count, count);//1 100000代表score的排序氛围值，即从1-100000的范围
//            log.debug("getPage {}", key);
//        } catch (Exception e) {
//            log.warn("getPage {}", key, e);
//        }
//        return result;
//    }
//
//    /**
//     * 计算key值对应的数量
//     * @param key
//     * @return
//     */
//    public static Integer getSize(String key){
//        Integer num = 0;
//        try {
//            Long size = redisTemplate.opsForZSet().zCard(key+":page");
//            log.debug("getSize {}", key);
//            return size.intValue();
//        } catch (Exception e) {
//            log.warn("getSize {}", key, e);
//        }
//        return num;
//    }




}
