package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.KeepRecord;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.KeepRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.service.InsureRecordService;
import com.woniu.service.OutimeRemindService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 保险
 */
@RestController
@CrossOrigin
public class InsureRecordController {

    @Autowired
    private InsureRecordService insureRecordService;

    //到期提醒
    @Autowired
    private OutimeRemindService outimeRemindService;

    @RequestMapping("/insureRecord/add")
    public int add(@RequestBody InsureRecordPo insureRecordPo){


        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(insureRecordPo.getCardId());
        orpo.setOutDate(insureRecordPo.getOutDate());
        orpo.setType(insureRecordPo.getType());

        //判断是否输入备注，如果没有自动添加备注
        if(insureRecordPo.getRemarks().equals(null) || insureRecordPo.getRemarks().equals("")){
            insureRecordPo.setRemarks("这个人很懒，还没有备注");
        }
        //添加到期提醒
        outimeRemindService.add(orpo);

        return insureRecordService.add(insureRecordPo);

    }
    @RequestMapping("/insureRecord/delete")
    public int delete(Integer id){
        InsureRecordPo inpo = new InsureRecordPo();
        inpo.setId(id);
        return insureRecordService.delete(inpo);

    }


    @RequestMapping("/insureRecord/update")
    public int update(@RequestBody InsureRecordPo insureRecordPo){
        //查询对应id的原属性
        InsureRecordPo findbyid = insureRecordService.findById(insureRecordPo);


        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(insureRecordPo.getCardId());//要修改的车牌id
        orpo.setStartcarId(findbyid.getCardId());//设置原车牌id

        orpo.setOutDate(insureRecordPo.getOutDate());//要修改的到期时间
        orpo.setType(insureRecordPo.getType());//要修改的到期类别
        orpo.setStarttype(findbyid.getType());//设置原到期类别

        outimeRemindService.update(orpo);//修改到期提醒
        //判断是否输入备注，如果没有自动添加备注
        if(insureRecordPo.getRemarks().equals(null) || insureRecordPo.getRemarks().equals("")){
            insureRecordPo.setRemarks("这个人很懒，还没有备注");
        }


        return insureRecordService.update(insureRecordPo);

    }

    //分页查询列表
    @RequestMapping("/insureRecord/list")
    public ResponseResult<PageInfo<InsureRecordPo>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=insureRecordService.count(searchText);
        List<InsureRecordPo> insurelist = insureRecordService.insureRecordList(searchText,pageIndex,pageSize);
        PageInfo<InsureRecordPo> pageInfo = new PageInfo<>(insurelist);
        pageInfo.setTotal(total);


        return new ResponseResult<>(pageInfo);
    }


    /**
     * 根据id查询
     *
     * @param
     * @return
     */
    @RequestMapping("/insureRecord/findbyid")
    public ResponseResult<InsureRecordPo> findbyid(Integer id) {
        InsureRecordPo inpo = new InsureRecordPo();
        inpo.setId(id);
        InsureRecordPo findbyid = insureRecordService.findById(inpo);

        return new ResponseResult<>(findbyid);
    }

}
