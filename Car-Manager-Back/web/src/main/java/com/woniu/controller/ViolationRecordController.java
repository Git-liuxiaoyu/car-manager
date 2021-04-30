package com.woniu.controller;


import com.woniu.domain.ViolationRecord;
import com.woniu.po.ViolationRecordPo;
import com.woniu.service.ViolationRecordService;
import com.woniu.service.ViolationRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/violation/")
public class ViolationRecordController {

    @Autowired
    private ViolationRecordService violationRecordService;


    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public List<ViolationRecord> getrecordlist(){
        List<ViolationRecord> list = violationRecordService.list();
        return list;
    }

    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(){

        ViolationRecordPo vrpo = new ViolationRecordPo();
        vrpo.setCarId(1);
        vrpo.setType(1);
        vrpo.setTime("2021-04-30 14:45:58");
        vrpo.setFee(new BigDecimal(300.0));
        vrpo.setReduceScore(6);
        vrpo.setDriverId(1);
        vrpo.setPlace("北京市朝阳区");
        vrpo.setRemarks("无");

        int i = violationRecordService.add(vrpo);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}
    }


    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(){
        ViolationRecordPo vrpo = new ViolationRecordPo();
        vrpo.setCarId(1);
        vrpo.setType(1);
        vrpo.setTime("2021-04-30 14:45:58");
        vrpo.setFee(new BigDecimal(400.0));
        vrpo.setReduceScore(12);
        vrpo.setDriverId(5);
        vrpo.setPlace("武汉黄冈");
        vrpo.setRemarks("无");
        vrpo.setId(3);

        int i = violationRecordService.update(vrpo);

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
        ViolationRecordPo vrpo = new ViolationRecordPo();
        vrpo.setId(2);

        int i = violationRecordService.delete(vrpo);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }

    /**
     * 根据id查询数据
     * @return
     */
    @RequestMapping("findbyid")
    public ResponseResult<ViolationRecordPo> findbyid(){
        ViolationRecordPo vrpo = new ViolationRecordPo();
        vrpo.setId(3);
        ViolationRecordPo feeRecordPo = violationRecordService.findbyid(vrpo);

        if(feeRecordPo != null){
            return new ResponseResult<ViolationRecordPo>(feeRecordPo);
        }else{
            return new ResponseResult(500,"查无此人");
        }
    }
}
