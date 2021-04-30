package com.woniu.controller;

import com.woniu.po.InsureRecordPo;
import com.woniu.service.InsureRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InsureRecordController {

    @Autowired
    private InsureRecordService insureRecordService;

    @RequestMapping("/insureRecord/add")
    public int add(InsureRecordPo insureRecordPo){
        return insureRecordService.add(insureRecordPo);

    }
    @RequestMapping("/insureRecord/delete")
    public int delete(InsureRecordPo insureRecordPo){
        return insureRecordService.delete(insureRecordPo);

    }
    @RequestMapping("/insureRecord/update")
    public int update(InsureRecordPo insureRecordPo){
        return insureRecordService.update(insureRecordPo);

    }
    @RequestMapping("/insureRecord/list")
    public List list(){
        List<InsureRecordPo> insureRecords = insureRecordService.insureRecordList();
        return insureRecords;
    }

}
