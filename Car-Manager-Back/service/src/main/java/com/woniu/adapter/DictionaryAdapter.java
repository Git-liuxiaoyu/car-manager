package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DictionaryDao;
import com.woniu.dao.FeeRecordDao;
import com.woniu.domain.Dictionary;
import com.woniu.domain.FeeRecord;
import com.woniu.po.DictionaryPo;
import com.woniu.po.FeeRecordPo;
import com.woniu.redis.DictionaryRedisDao;
import com.woniu.redis.FeeRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DictionaryAdapter {


    @Autowired
    private DictionaryRedisDao dictionaryRedisDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    /**
     * 按照patentid查询
     * @return
     */
    public List<Dictionary> types(DictionaryPo dictionaryPo){
        List<DictionaryPo> typelist = dictionaryRedisDao.list(dictionaryPo.getParentId());
        ObjectMapper objectMapper = new ObjectMapper();
        if(typelist.size() == 0){
            //从数据库查数据
            typelist = dictionaryDao.types(dictionaryPo);
            //存入redis的缓存中
            dictionaryRedisDao.addRedisDriverList(typelist,dictionaryPo.getParentId());
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<Dictionary> dictions = new ArrayList<Dictionary>();
        for(DictionaryPo frpo : typelist) {
            Dictionary item = new Dictionary();
            BeanUtils.copyProperties(frpo, item);
            dictions.add(item);
        }
        return dictions;
    }


}
