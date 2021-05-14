package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.service.OutimeRemindService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 到期提醒
 */
@RestController
@CrossOrigin
public class OutimeRemindController {

    @Autowired
    private OutimeRemindService outimeRemindService;

    @RequestMapping("/outimeRemind/add")
    public int add(OutimeRemindPo outimeRemindPo){
        return outimeRemindService.add(outimeRemindPo);

    }
    @RequestMapping("/outimeRemind/delete")
    public int delete(OutimeRemindPo outimeRemindPo){
        return outimeRemindService.delete(outimeRemindPo);

    }
    @RequestMapping("/outimeRemind/update")
    public int update(OutimeRemindPo outimeRemindPo){
        return outimeRemindService.update(outimeRemindPo);

    }


    //分页查询列表
    @RequestMapping("/outimeRemind/list")
    public ResponseResult<PageInfo<OutimeRemindPo>> list(Integer p, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }

        //定义时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();//加日期对象

        Date date = new Date();//当前系统时间
        String thistime = df.format(date);// thistime为当前时间



        calendar.setTime(date);
        calendar.add(calendar.DATE,7);//把当前系统时间往后加7天
        date = calendar.getTime();
        //格式化日期格式把日期作为查询条件
        String nexttime = df.format(date);//nexttime为距离当前时间七天后



        Integer total=outimeRemindService.count(nexttime,thistime);
        List<OutimeRemindPo> outimeReminds = outimeRemindService.outimeRemindList(nexttime,thistime,pageIndex,pageSize);
        PageInfo<OutimeRemindPo> pageInfo = new PageInfo<>(outimeReminds);
        pageInfo.setTotal(total);


        return new ResponseResult<>(pageInfo);
    }

}
