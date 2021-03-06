package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Dictionary;
import com.woniu.domain.OppositeCompany;

import com.woniu.po.DictionaryPo;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.po.ViolationRecordPo;
import com.woniu.service.DictionaryService;
import com.woniu.service.OilRecordService;
import com.woniu.service.OppositeCompanyService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 往来单位
 */
@CrossOrigin
@RestController
@RequestMapping("/opposite/")
public class OppositeCompanyController {

    @Autowired
    private OppositeCompanyService oppositeCompanyService;


    @Autowired
    private OilRecordService oilRecordService;


    //分页查询列表
    @RequestMapping("/list")
    public ResponseResult<PageInfo<OppositeCompany>> oppositelist(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=oppositeCompanyService.count(searchText);
        List<OppositeCompany> insurelist = oppositeCompanyService.List(searchText,pageIndex,pageSize);
        PageInfo<OppositeCompany> pageInfo = new PageInfo<>(insurelist);
        pageInfo.setTotal(total);


        return new ResponseResult<>(pageInfo);
    }


    /**
     * 添加
     *
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(@RequestBody OppositeCompanyPo ocpo) {
        //判断是否输入备注，如果没有自动添加备注
        if(ocpo.getRemarks().equals(null) || ocpo.getRemarks().equals("")){
            ocpo.setRemarks("这个人很懒，还没有备注");
        }
        oppositeCompanyService.add(ocpo);
        return new ResponseResult(200, "添加成功");
    }


    /**
     * 根据id查询
     *
     * @param
     * @return
     */
    @RequestMapping("findbyid")
    public ResponseResult<OppositeCompanyPo> findbyid(Integer id) {
        OppositeCompanyPo oppo = new OppositeCompanyPo();
        oppo.setId(id);
        OppositeCompanyPo findbyid = oppositeCompanyService.findbyid(oppo);

        return new ResponseResult<>(findbyid);
    }


    /**
     * 动态修改
     *
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(@RequestBody OppositeCompanyPo ocpo) {
        //判断是否输入备注，如果没有自动添加备注
        if(ocpo.getRemarks().equals(null) || ocpo.getRemarks().equals("")){
            ocpo.setRemarks("这个人很懒，还没有备注");
        }

        int i = oppositeCompanyService.update(ocpo);

        if (i > 0) {
            return new ResponseResult(200, "修改成功");
        } else {
            return new ResponseResult(500, "修改失败");
        }

    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("delete")
    public ResponseResult delete(int id) {
        OppositeCompanyPo ocpo = new OppositeCompanyPo();
        ocpo.setId(id);
        int i = oppositeCompanyService.delete(ocpo);

        if (i > 0) {
            return new ResponseResult(200, "删除成功");
        } else {
            return new ResponseResult(500, "删除失败");
        }

    }


    //查询往来单位下拉框
    @RequestMapping("getoppolist")
    public List<OppositeCompanyPo> oppolist(Integer type){
        List<OppositeCompanyPo> oppolist = oilRecordService.oppolist(type);

        return oppolist;
    }


}
