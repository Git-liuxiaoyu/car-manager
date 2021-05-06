package com.woniu.domain;

import lombok.Data;

/**
 * service 字典实体类
 */
@Data
public class Dictionary {

    private Integer id;
    private Integer parentId;//父级元素id
    private String text;//元素描述
    private Integer level;//元素级别
    private Integer isDisable;//是否启用
}
