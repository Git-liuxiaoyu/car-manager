package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.AccidentRecord;
import com.woniu.domain.FeeRecord;
import com.woniu.po.AccidentRecordPo;
import com.woniu.po.CarPo;
import com.woniu.service.AccidentRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AccidentRecordController {

    @Autowired
    private AccidentRecordService accidentRecordService;

    @RequestMapping("/accidentRecord/add")
    public ResponseResult add(@RequestBody AccidentRecord accidentRecord){
        int i = accidentRecordService.add(accidentRecord);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}

    }
    @RequestMapping("/accidentRecord/delete")
    public ResponseResult delete(Integer id){
        int i = accidentRecordService.delete(id);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }
    @RequestMapping("/accidentRecord/update")
    public ResponseResult update(@RequestBody AccidentRecord accidentRecord){
        System.out.println(accidentRecord);
        int i = accidentRecordService.update(accidentRecord);
        if(i > 0){return new ResponseResult(200,"修改成功");}
        else{return new ResponseResult(500,"修改失败");}

    }
    @RequestMapping("/accidentRecord/list")
    public ResponseResult<PageInfo<AccidentRecord>> list(Integer p, String searchText, Integer size){
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=accidentRecordService.count(searchText);
//        List<Employee> employeeList = employeeService.findAll(searchText,pageIndex,pageSize);
        List<AccidentRecord> accidentRecordList = accidentRecordService.list(searchText, pageIndex, pageSize);
        PageInfo<AccidentRecord> pageInfo = new PageInfo<>(accidentRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
//        return accidentRecords;
    }

}
