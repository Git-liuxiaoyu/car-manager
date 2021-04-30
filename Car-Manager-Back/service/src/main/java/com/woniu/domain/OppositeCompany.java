package com.woniu.domain;

import lombok.Data;

/**
 * 往来单位service实体类
 */
@Data
public class OppositeCompany {
    private Integer id;
    private String name;//单位名
    private Integer type;//单位类型 字典外键
    private String address;//地址
    private String phone;//单位电话
    private String linkName;//联系人姓名
    private String remarks;//备注
    private Integer status;//是否启用
}
