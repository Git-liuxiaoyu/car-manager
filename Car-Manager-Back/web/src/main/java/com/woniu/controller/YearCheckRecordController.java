package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.YearCheckRecord;
import com.woniu.service.YearCheckRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/yearCheckRecord")
@CrossOrigin
public class YearCheckRecordController {

    @Autowired
    private YearCheckRecordService yearCheckRecordService;

    @RequestMapping("/list")
    public ResponseResult<PageInfo<YearCheckRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=yearCheckRecordService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<YearCheckRecord> yearCheckRecordList = yearCheckRecordService.list(searchText,pageIndex,pageSize);
        PageInfo<YearCheckRecord> pageInfo = new PageInfo<>(yearCheckRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody YearCheckRecord yearCheckRecord){
        yearCheckRecordService.add(yearCheckRecord);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody YearCheckRecord yearCheckRecord){
        yearCheckRecordService.update(yearCheckRecord);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        yearCheckRecordService.delete(id);
        return ResponseResult.SUCCESS;
    }

}
