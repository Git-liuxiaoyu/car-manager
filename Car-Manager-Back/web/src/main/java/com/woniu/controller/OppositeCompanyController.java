package com.woniu.controller;

import com.woniu.domain.Dictionary;
import com.woniu.domain.OppositeCompany;

import com.woniu.po.DictionaryPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.po.ViolationRecordPo;
import com.woniu.service.DictionaryService;
import com.woniu.service.OppositeCompanyService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/opposite/")
public class OppositeCompanyController {

    @Autowired
    private OppositeCompanyService oppositeCompanyService;

    @Autowired
    private DictionaryService dictionaryService;


    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public List<OppositeCompany> oppositelist() {
        List<OppositeCompany> list = oppositeCompanyService.List();
        System.out.println("列表");
        return list;
    }


    /**
     * 添加
     *
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResponseResult add(@RequestBody OppositeCompanyPo ocpo) {
        System.out.println("添加");
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
    public ResponseResult<OppositeCompanyPo> findbyid(Integer id, Integer type) {


        OppositeCompanyPo ocpo = new OppositeCompanyPo();
        ocpo.setId(id);
        OppositeCompanyPo po = oppositeCompanyService.findbyid(ocpo);
        //查询字典里单位类型
        DictionaryPo dipo = new DictionaryPo();
        dipo.setParentId(30);
        List<Dictionary> types = dictionaryService.types(dipo);
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getId() == type) {
                po.setTypename(types.get(i).getText());
            }
        }
        if (po != null) {
            return new ResponseResult<OppositeCompanyPo>(po);
        } else {
            return new ResponseResult(500, "失败");
        }

    }


    /**
     * 动态修改
     *
     * @return
     */
    @RequestMapping("update")
    public ResponseResult update(@RequestBody OppositeCompanyPo ocpo) {
        System.out.println("修改");

        System.out.println(ocpo.getStatus());
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


}
