package com.woniu.controller;

import com.woniu.domain.AccidentRecord;
import com.woniu.po.AccidentRecordPo;
import com.woniu.po.CarPo;
import com.woniu.service.AccidentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccidentRecordController {

    @Autowired
    private AccidentRecordService accidentRecordService;

    @RequestMapping("/accidentRecord/add")
    public int add(AccidentRecordPo accidentRecordPo){
        return accidentRecordService.add(accidentRecordPo);

    }
    @RequestMapping("/accidentRecord/delete")
    public int delete(AccidentRecordPo accidentRecordPo){
        return accidentRecordService.delete(accidentRecordPo);

    }
    @RequestMapping("/accidentRecord/update")
    public int update(AccidentRecordPo accidentRecordPo){
        return accidentRecordService.update(accidentRecordPo);

    }
    @RequestMapping("/accidentRecord/list")
    public List list(String carNum){
        AccidentRecordPo accidentRecordPo = new AccidentRecordPo();
        CarPo carPo = new CarPo();
        carPo.setCarNum(carNum);
        accidentRecordPo.setCarPo(carPo);
        List<AccidentRecordPo> accidentRecords = accidentRecordService.accidentRecordList(accidentRecordPo);
        return accidentRecords;
    }

}
