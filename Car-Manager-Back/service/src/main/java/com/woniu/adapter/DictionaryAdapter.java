package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DictionaryDao;
import com.woniu.domain.Dictionary;
import com.woniu.po.DictionaryPo;
import com.woniu.redis.DictionaryRedisDao;
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
    public List<Dictionary> findDictionaryLists(String searchText, int pageIndex, int pageSize){
        List<DictionaryPo>  dictionaryPoList= dictionaryRedisDao.list(searchText,pageIndex,pageSize);
        ObjectMapper objectMapper = new ObjectMapper();
        if(dictionaryPoList.size() == 0){
            //从数据库查数据
            dictionaryPoList = dictionaryDao.list(searchText);
            //存入redis的缓存中
            dictionaryRedisDao.addRedisDriverList(dictionaryPoList,searchText,pageIndex,pageSize);
        }
        //把dao的 RoleList RolePo --- 转成  List<Role>
        List<Dictionary> dictionaryList = new ArrayList<>();
        for(DictionaryPo d : dictionaryPoList) {
            Dictionary item = new Dictionary();
            BeanUtils.copyProperties(d, item);
            dictionaryList.add(item);
        }
        return dictionaryList;
    }


    public Integer count(String searchText) {
        return dictionaryDao.count(searchText);
    }

    public List<DictionaryPo> list() {
        return dictionaryDao.menuList();
    }

    public void add(Dictionary dictionary) {
        DictionaryPo item = new DictionaryPo();
        BeanUtils.copyProperties(dictionary, item);
        dictionaryDao.add(item);
        dictionaryRedisDao.updateRedis();
    }

    public List<Dictionary> listGetByParentId(Integer id) {
        //查询所有
        List<DictionaryPo> dictionaryPoList = dictionaryDao.listGetByParentId(id);
        //转对象
        List<Dictionary> dictionaryList = new ArrayList<>();
        for(DictionaryPo d : dictionaryPoList) {
            Dictionary item = new Dictionary();
            BeanUtils.copyProperties(d, item);
            dictionaryList.add(item);
        }
        return dictionaryList;
    }

    public void dalete(Integer id) {
        dictionaryDao.delete(id);
        dictionaryRedisDao.updateRedis();
    }

    public void update(Dictionary dictionary) {
        DictionaryPo item = new DictionaryPo();
        BeanUtils.copyProperties(dictionary, item);
        dictionaryDao.update(item);
        dictionaryRedisDao.updateRedis();
    }
}
