package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.KeepRecord;
import com.woniu.domain.OilRecord;
import com.woniu.po.KeepRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.KeepRecordService;
import com.woniu.service.OilRecordService;
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
@RequestMapping("/keeprecord/")
public class KeepRecordController {

    @Autowired
    private KeepRecordService keepRecordService;

    @Autowired
    private OilRecordService oilRecordService;


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
        PageHelper.startPage(pageIndex,pageSize);
        List<KeepRecord> keepList = keepRecordService.keepList(searchText,pageIndex,pageSize);
        PageInfo<KeepRecord> pageInfo = new PageInfo<>(keepList);
        pageInfo.setTotal(total);
        //System.out.println(pageInfo);

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
        System.out.println("要修改的数据"+krpo);
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
