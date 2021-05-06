package com.woniu.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/10:11
 * @Description:
 */
@Data
public class EmployeePo {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private Integer age;
    private Integer gender;
    private String telephone;
    private String deptId;
    private String positionId;
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    private String idCard;
    @JSONField(format = "yyyy-MM-dd")
    private Date entryDate;
    private String address;
    private Integer status;
}
