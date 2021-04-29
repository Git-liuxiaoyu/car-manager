package com.woniu.controller;

import com.woniu.domin.OppositeCompany;

import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.OppositeCompanyService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/opposite/")
public class OppositeCompanyController {

    @Autowired
    private OppositeCompanyService oppositeCompanyService;


    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public List<OppositeCompany> oppositelist(){
        List<OppositeCompany> list = oppositeCompanyService.List();
        return list;
    }

    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(){

        OppositeCompanyPo ocpo = new OppositeCompanyPo();
        ocpo.setName("腾讯");
        ocpo.setType(1);
        ocpo.setPhone("10086");
        ocpo.setAddress("武汉");
        ocpo.setLinkName("马化腾");
        ocpo.setRemarks("无");
        ocpo.setStatus(1);

        oppositeCompanyService.add(ocpo);
        return new ResponseResult(200,"添加成功");
    }


    /**
     * 动态修改
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(){
        OppositeCompanyPo ocpo = new OppositeCompanyPo();
        ocpo.setName("京东");
        ocpo.setType(1);
        ocpo.setPhone("10010");
        ocpo.setAddress("北京");
        //ocpo.setLinkName("马化腾");
        ocpo.setRemarks("无");
        ocpo.setStatus(1);
        ocpo.setId(3);
        int i = oppositeCompanyService.update(ocpo);

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
        OppositeCompanyPo ocpo = new OppositeCompanyPo();
        ocpo.setId(3);
        int i = oppositeCompanyService.delete(ocpo);

        if(i > 0){return new ResponseResult(200,"删除成功");}
        else{return new ResponseResult(500,"删除失败");}

    }






}
