package com.woniu.controller;

import com.woniu.po.OutimeRemindPo;
import com.woniu.service.OutimeRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    @RequestMapping("/outimeRemind/list")
    public List list(){
        List<OutimeRemindPo> outimeReminds = outimeRemindService.outimeRemindList();
        return outimeReminds;
    }

}
