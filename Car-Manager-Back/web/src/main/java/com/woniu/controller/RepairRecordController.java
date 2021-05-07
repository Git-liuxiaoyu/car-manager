package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.RepairRecord;
import com.woniu.domain.RepairRecord;
import com.woniu.service.RepairRecordService;
import com.woniu.service.RepairRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/repairRecord")
@CrossOrigin
public class RepairRecordController {

    @Autowired
    private RepairRecordService repairRecordService;

    @RequestMapping("/list")
    public ResponseResult<PageInfo<RepairRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=repairRecordService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<RepairRecord> repairRecordList = repairRecordService.list(searchText,pageIndex,pageSize);
        PageInfo<RepairRecord> pageInfo = new PageInfo<>(repairRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody RepairRecord repairRecord){
        repairRecordService.add(repairRecord);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody RepairRecord repairRecord){
        repairRecordService.update(repairRecord);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        repairRecordService.delete(id);
        return ResponseResult.SUCCESS;
    }

}
