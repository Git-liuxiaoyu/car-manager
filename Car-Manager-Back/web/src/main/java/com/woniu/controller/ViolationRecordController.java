package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.ViolationRecord;
import com.woniu.service.ViolationRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/violationRecord")
@CrossOrigin
public class ViolationRecordController {

    @Autowired
    private ViolationRecordService violationRecordService;

    @RequestMapping("/list")
    public ResponseResult<PageInfo<ViolationRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=violationRecordService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<ViolationRecord> violationRecordList = violationRecordService.list(searchText,pageIndex,pageSize);
        PageInfo<ViolationRecord> pageInfo = new PageInfo<>(violationRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody ViolationRecord violationRecord){
        violationRecordService.add(violationRecord);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody ViolationRecord violationRecord){
        violationRecordService.update(violationRecord);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        violationRecordService.delete(id);
        return ResponseResult.SUCCESS;
    }

}
