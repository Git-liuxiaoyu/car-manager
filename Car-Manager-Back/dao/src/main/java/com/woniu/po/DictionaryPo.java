package com.woniu.po;

import lombok.Data;

import java.util.List;

/**
 * 字典类
 */
@Data
public class DictionaryPo {
    private Integer id;
    private Integer parentId;//父级元素id
    private String text;//元素描述
    private Integer level;//元素级别
    private Integer isDisable;//是否启用
    private List<DictionaryPo> children;
}
