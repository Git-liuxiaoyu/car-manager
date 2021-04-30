package com.woniu.controller;

import com.woniu.po.YearCheckRecordPo;
import com.woniu.service.YearCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class YearCheckRecordController {

    @Autowired
    private YearCheckRecordService yearCheckRecordService;

    @RequestMapping("/yearCheckRecord/add")
    public int add(YearCheckRecordPo yearCheckRecordPo){
        return yearCheckRecordService.add(yearCheckRecordPo);

    }
    @RequestMapping("/yearCheckRecord/delete")
    public int delete(YearCheckRecordPo yearCheckRecordPo){
        return yearCheckRecordService.delete(yearCheckRecordPo);

    }
    @RequestMapping("/yearCheckRecord/update")
    public int update(YearCheckRecordPo yearCheckRecordPo){
        return yearCheckRecordService.update(yearCheckRecordPo);

    }
    @RequestMapping("/yearCheckRecord/list")
    public List list(){
        List<YearCheckRecordPo> yearCheckRecords = yearCheckRecordService.yearCheckRecordList();
        return yearCheckRecords;
    }

}
