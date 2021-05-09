package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.KeepRecord;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.InsureRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class InsureRecordController {

    @Autowired
    private InsureRecordService insureRecordService;

    @RequestMapping("/insureRecord/add")
    public int add(@RequestBody InsureRecordPo insureRecordPo){
        System.out.println("要添加的数据"+insureRecordPo);

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
        PageHelper.startPage(pageIndex,pageSize);
        List<InsureRecordPo> insurelist = insureRecordService.insureRecordList(searchText,pageIndex,pageSize);
        PageInfo<InsureRecordPo> pageInfo = new PageInfo<>(insurelist);
        pageInfo.setTotal(total);
        //System.out.println(pageInfo);

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
        System.out.println("查询到的修改数据"+findbyid);
        return new ResponseResult<>(findbyid);
    }

}
