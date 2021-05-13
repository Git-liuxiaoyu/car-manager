package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.YearCheckRecord;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.po.YearCheckRecordPo;
import com.woniu.service.OutimeRemindService;
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

    //到期提醒
    @Autowired
    private OutimeRemindService outimeRemindService;

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
        List<YearCheckRecord> yearCheckRecordList = yearCheckRecordService.list(searchText,pageIndex,pageSize);
        PageInfo<YearCheckRecord> pageInfo = new PageInfo<>(yearCheckRecordList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody YearCheckRecord yearCheckRecord){
        if(yearCheckRecord.getNextCheckDate().isEmpty()){
            String checkDate = yearCheckRecord.getCheckDate();
            // TODO: 2021/5/13 下次年检时间加一年
        }
        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(yearCheckRecord.getCarId());
        orpo.setOutDate(yearCheckRecord.getNextCheckDate());
        orpo.setType(106);
        //添加到期提醒
        outimeRemindService.add(orpo);

        yearCheckRecordService.add(yearCheckRecord);
        return ResponseResult.SUCCESS;
    }



    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody YearCheckRecord yearCheckRecord){

        //查询对应id的原属性
        YearCheckRecordPo findbyid = yearCheckRecordService.findById(yearCheckRecord);
        System.out.println("修改原属性"+findbyid);

        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(yearCheckRecord.getCarId());//要修改的车牌id
        orpo.setStartcarId(findbyid.getCarId());//设置原车牌id

        orpo.setOutDate(yearCheckRecord.getNextCheckDate());//要修改的到期时间
        orpo.setStartoutDate(findbyid.getNextCheckDate());//设置原到期时间

        System.out.println("要修改的保养到期数据"+orpo);


        outimeRemindService.update(orpo);//修改到期提醒

        yearCheckRecordService.update(yearCheckRecord);




        return ResponseResult.SUCCESS;
    }



    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        yearCheckRecordService.delete(id);
        return ResponseResult.SUCCESS;
    }

}
