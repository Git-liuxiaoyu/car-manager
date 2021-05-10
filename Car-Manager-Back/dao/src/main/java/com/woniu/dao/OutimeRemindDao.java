package com.woniu.dao;

import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

//到期提醒
public interface OutimeRemindDao {

    //public List<OutimeRemindPo> outimeRemindList();

    //查询到期提醒
    List<OutimeRemindPo> outimeRemindList(@Param("nexttime") String nexttime,@Param("thistime") String thistime);

    //总数
    int count(@Param("nexttime") String nexttime,@Param("thistime") String thistime);


    public int add(OutimeRemindPo outimeRemindPo);

    public int update(OutimeRemindPo outimeRemindPo);

    public int delete(OutimeRemindPo outimeRemindPo);

    public OutimeRemindPo findById(OutimeRemindPo outimeRemindPo);
}
