package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.dao.OilRecordDao;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Employee;
import com.woniu.domain.OilRecord;
import com.woniu.domain.ViolationRecord;
import com.woniu.po.*;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.DictionaryService;
import com.woniu.service.OilRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/oilrecord")
public class OilRecordController {

    @Autowired
    private OilRecordService oilRecordService;

    @Autowired
    private DictionaryService dictionaryService;

    //查询车牌号下拉框
    @RequestMapping("carlist")
    public List<CarPo> carlist(){
        List<CarPo> carlist = oilRecordService.carlist();
        return carlist;
    }


    //查询往来单位下拉框
    @RequestMapping("oppolist")
    public List<OppositeCompanyPo> oppolist(){
        List<OppositeCompanyPo> oppolist = oilRecordService.oppolist(31);
        return oppolist;
    }

    //查询加油人员下拉框
    @RequestMapping("driverlist")
    public List<DriverPo> driverlist(){
        List<DriverPo> driverlist = oilRecordService.driverlist();

        return driverlist;
    }





    @RequestMapping("/list")
    public ResponseResult<PageInfo<OilRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=oilRecordService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
        List<OilRecord> oilreList = oilRecordService.oilRecordList(searchText,pageIndex,pageSize);
        PageInfo<OilRecord> pageInfo = new PageInfo<>(oilreList);
        pageInfo.setTotal(total);

        return new ResponseResult<>(pageInfo);
    }


    @RequestMapping("/add")
    public ResponseResult add(@RequestBody OilRecord oilRecord){
        System.out.println("添加："+oilRecord);
        oilRecordService.add(oilRecord);
        return ResponseResult.SUCCESS;

    }

    /**
     * 修改
     * @param oilRecord
     * @return
     */
    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody OilRecord oilRecord){

        System.out.println("要修改的数据："+oilRecord);
        int i = oilRecordService.update(oilRecord);
        if (i > 0) {
            return new ResponseResult(200, "修改成功");
        } else {
            return new ResponseResult(500, "修改失败");
        }
    }

    @RequestMapping("delete")
    public ResponseResult delete(Integer id){

        int i = oilRecordService.delete(id);
        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}
    }



    @RequestMapping("findbyid")
    public OilRecordPo findbyid(Integer id){
        OilRecordPo orpo = new OilRecordPo();
        orpo.setId(id);
        OilRecordPo po = oilRecordService.findbyid(orpo);
        return po;
    }





}
