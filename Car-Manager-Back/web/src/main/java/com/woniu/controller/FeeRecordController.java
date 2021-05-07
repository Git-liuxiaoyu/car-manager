package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.*;
import com.woniu.po.CarPo;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;
import com.woniu.service.DriverService;
import com.woniu.service.FeeRecordService;
import com.woniu.service.GetRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/feerecord/")
public class FeeRecordController {

    @Autowired
    private FeeRecordService feeRecordService;
    @Autowired
    private DriverService driverService;

    /**
     * 列表
     * @return
     */
//    @RequestMapping("list")
//    public List<FeeRecord> getrecordlist(){
//        List<FeeRecord> list = feeRecordService.list();
//        return list;
//    }
    @RequestMapping("list")
    public ResponseResult<PageInfo<FeeRecord>> getrecordlist(Integer p, String searchText, Integer size){
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=feeRecordService.count(searchText);
        PageHelper.startPage(pageIndex,pageSize);
//        List<Employee> employeeList = employeeService.findAll(searchText,pageIndex,pageSize);
        List<FeeRecord> feeRecordList = feeRecordService.list(searchText, pageIndex, pageSize);
        PageInfo<FeeRecord> pageInfo = new PageInfo<>(feeRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }


    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(@RequestBody FeeRecordPo feeRecordPo){

//        FeeRecordPo frpo = new FeeRecordPo();
//        frpo.setCarId(1);
//        frpo.setFeeName("测试规费名称三");
//        frpo.setPayTime("2021-05-03 15:17:01");
//        frpo.setFee(new BigDecimal(200.00));
//        frpo.setOppositeCompanyId(1);
//        frpo.setDriverId(2);
//        frpo.setRemarks("无");

//        int i = feeRecordService.add(frpo);
        int i = feeRecordService.add(feeRecordPo);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}
    }


    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(@RequestBody FeeRecord feeRecord){
//        FeeRecordPo frpo = new FeeRecordPo();
//        frpo.setCarId(1);
//        frpo.setFeeName("测试修改");
//        frpo.setPayTime("2021-05-03 15:17:01");
//        frpo.setFee(new BigDecimal(300.00));
//        frpo.setOppositeCompanyId(1);
//        frpo.setDriverId(2);
//        frpo.setRemarks("有");
//        frpo.setId(3);

        int i = feeRecordService.update(feeRecord);

        if(i >0){
            return new ResponseResult(200,"修改成功");
        }else {
            return new ResponseResult(500, "修改失败");
        }

    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    public ResponseResult delete(Integer id){
        FeeRecord feeRecord = new FeeRecord();
        feeRecord.setId(id);

        int i = feeRecordService.delete(feeRecord);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }

    @RequestMapping("findbyid")
    public ResponseResult<FeeRecordPo> findbyid(){
        FeeRecordPo frpo = new FeeRecordPo();
        frpo.setId(4);
        FeeRecordPo feeRecordPo = feeRecordService.findbyid(frpo);

        if(feeRecordPo != null){
            return new ResponseResult<FeeRecordPo>(feeRecordPo);
        }else{
            return new ResponseResult(500,"查无此人");
        }
    }
    @RequestMapping("getCarNum")
    public ResponseResult<List<CarPo>> getAllCarNum(){
        List<CarPo> allCarNum = feeRecordService.getAllCarNum();
        return  new ResponseResult<>(allCarNum);
    }

    @RequestMapping("getDriverName")
    public ResponseResult<List<Driver>> getDriverName(){
        List<Driver> driverList = driverService.getAll();
        return  new ResponseResult<>(driverList);
    }


}
