package com.woniu.controller;

import com.woniu.domain.KeepRecord;
import com.woniu.po.KeepRecordPo;
import com.woniu.service.KeepRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/keeprecord/")
public class KeepRecordController {

    @Autowired
    private KeepRecordService keepRecordService;


    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public List<KeepRecord> getrecordlist(){
        List<KeepRecord> list = keepRecordService.list();
        return list;
    }

    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(){

        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setCarId(1);
        krpo.setType(1);
        krpo.setKeepDate("2021-04-30 14:45:58");
        krpo.setFee(new BigDecimal(200.00));
        krpo.setOppositeCompanyId(2);
        krpo.setThisKeepMileage(200.0);
        krpo.setKeepContext("全车保养");
        krpo.setNextKeepTime("2021-04-30 14:45:58");
        krpo.setNextKeepMileage(200.0);
        krpo.setDriverId(1);
        krpo.setRemarks("无");

        int i = keepRecordService.add(krpo);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}
    }


    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(){
        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setCarId(2);
        krpo.setType(2);
        krpo.setKeepDate("2021-04-30 14:45:58");
        krpo.setFee(new BigDecimal(100.00));
        krpo.setOppositeCompanyId(2);
        krpo.setThisKeepMileage(200.0);
        krpo.setKeepContext("半车保养");
        krpo.setNextKeepTime("2021-04-30 14:45:58");
        krpo.setNextKeepMileage(100.0);
        krpo.setDriverId(1);
        krpo.setRemarks("无");
        krpo.setId(2);

        int i = keepRecordService.update(krpo);

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
    public ResponseResult delete(){
        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setId(2);

        int i = keepRecordService.delete(krpo);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }

    @RequestMapping("findbyid")
    public ResponseResult<KeepRecordPo> findbyid(){
        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setId(1);
        KeepRecordPo feeRecordPo = keepRecordService.findbyid(krpo);

        if(feeRecordPo != null){
            return new ResponseResult<KeepRecordPo>(feeRecordPo);
        }else{
            return new ResponseResult(500,"查无此人");
        }
    }
}
