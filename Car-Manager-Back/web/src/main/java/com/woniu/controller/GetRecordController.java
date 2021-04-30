package com.woniu.controller;

import com.woniu.domain.GetRecord;
import com.woniu.domain.OppositeCompany;
import com.woniu.po.GetRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.GetRecordService;
import com.woniu.service.OppositeCompanyService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 取车记录controller
 *
 */
@RestController
@RequestMapping("/getrecord/")
public class GetRecordController {


    @Autowired
    private GetRecordService getRecordService;


    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public List<GetRecord> getrecordlist(){
        List<GetRecord> list = getRecordService.list();
        //System.out.println(list.get(0).getOcpo().getName());
        return list;
    }

    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(){

        GetRecordPo grpo = new GetRecordPo();
        grpo.setCarId(2);
        grpo.setOppositeCompanyId(2);
        grpo.setReapairType(1);
        grpo.setSendTime("2021-05-01 15:17:01");
        grpo.setGetTime("2021-05-01 15:17:01");
        grpo.setCost(new BigDecimal(3000.00));
        grpo.setRepairItem("更换玻璃");
        grpo.setMaterial("中国制造");
        grpo.setRemarks("无");

        int i = getRecordService.add(grpo);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}
    }


    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(){
        GetRecordPo grpo = new GetRecordPo();
        grpo.setCarId(1);
        grpo.setOppositeCompanyId(1);
        grpo.setReapairType(2);
        grpo.setSendTime("2021-05-01 15:17:01");
        grpo.setGetTime("2021-05-03 15:17:01");
        grpo.setCost(new BigDecimal(4000.0));
        grpo.setRepairItem("更换玻璃");
        grpo.setMaterial("中国制造");
        grpo.setRemarks("无");
        grpo.setId(5);
        int i = getRecordService.update(grpo);

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
        GetRecordPo grpo = new GetRecordPo();
        grpo.setId(5);

        int i = getRecordService.delete(grpo);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }

    @RequestMapping("findbyid")
    public ResponseResult<GetRecordPo> findbyid(){
        GetRecordPo grpo = new GetRecordPo();
        grpo.setId(10);
        GetRecordPo getRecordPo = getRecordService.findbyid(grpo);

        if(getRecordPo != null){
            return new ResponseResult<GetRecordPo>(getRecordPo);
        }else{
            return new ResponseResult(500,"查无此人");
        }
    }


}
