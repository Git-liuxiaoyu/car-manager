package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.KeepRecord;
import com.woniu.domain.OilRecord;
import com.woniu.po.KeepRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.service.KeepRecordService;
import com.woniu.service.OilRecordService;
import com.woniu.service.OutimeRemindService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


/**
 * 保养记录
 */
@RestController
@CrossOrigin
@RequestMapping("/keeprecord/")
public class KeepRecordController {

    @Autowired
    private KeepRecordService keepRecordService;

    @Autowired
    private OilRecordService oilRecordService;

    //到期提醒
    @Autowired
    private OutimeRemindService outimeRemindService;


    //分页查询列表
    @RequestMapping("/list")
    public ResponseResult<PageInfo<KeepRecord>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=keepRecordService.count(searchText);
        List<KeepRecord> keepList = keepRecordService.keepList(searchText,pageIndex,pageSize);
        PageInfo<KeepRecord> pageInfo = new PageInfo<>(keepList);
        pageInfo.setTotal(total);


        return new ResponseResult<>(pageInfo);
    }


    //查询往来单位下拉框
    @RequestMapping("oppolist")
    public List<OppositeCompanyPo> oppolist(){
        List<OppositeCompanyPo> oppolist = oilRecordService.oppolist(30);
        return oppolist;
    }




    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(@RequestBody KeepRecordPo keepRecordPo){

        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(keepRecordPo.getCarId());
        orpo.setOutDate(keepRecordPo.getNextKeepTime());
        orpo.setType(keepRecordPo.getType());
        //添加到期提醒
        outimeRemindService.add(orpo);

        //判断是否输入备注，如果没有自动添加备注
        if(keepRecordPo.getRemarks().equals(null) || keepRecordPo.getRemarks().equals("")){
            keepRecordPo.setRemarks("这个人很懒，还没有备注");
        }

        int i = keepRecordService.add(keepRecordPo);
        if(i > 0){return new ResponseResult(200,"添加成功");}
        else{return new ResponseResult(500,"添加失败");}
    }



    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(@RequestBody KeepRecordPo krpo){
        //判断是否输入备注，如果没有自动添加备注
        if(krpo.getRemarks().equals(null) || krpo.getRemarks().equals("")){
            krpo.setRemarks("这个人很懒，还没有备注");
        }
        //查询对应id的原属性
        KeepRecordPo findbyid = keepRecordService.findbyid(krpo);

        int i = keepRecordService.update(krpo);//修改



        OutimeRemindPo orpo = new OutimeRemindPo();
        orpo.setCarId(krpo.getCarId());//要修改的车牌id
        orpo.setStartcarId(findbyid.getCarId());//设置原车牌id

        orpo.setOutDate(krpo.getNextKeepTime());//要修改的到期时间
        orpo.setType(krpo.getType());//要修改的到期类别
        orpo.setStarttype(findbyid.getType());//设置原到期类别


        outimeRemindService.update(orpo);

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
        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setId(id);

        int i = keepRecordService.delete(krpo);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }

    @RequestMapping("findbyid")
    public ResponseResult<KeepRecordPo> findbyid(Integer id){
        KeepRecordPo krpo = new KeepRecordPo();
        krpo.setId(id);
        KeepRecordPo feeRecordPo = keepRecordService.findbyid(krpo);

        if(feeRecordPo != null){
            return new ResponseResult<KeepRecordPo>(feeRecordPo);
        }else{
            return new ResponseResult(500,"查无此人");
        }
    }



}
