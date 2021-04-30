package com.woniu.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/10:23
 * @Description:
 */
@Data
public class Employee {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private Integer age;
    private Integer gender;
    private String telephone;
    private String deptId;
    private String positionId;
    private Date birthday;
    private String idCard;
    private Date entryDate;
    private String address;
    private Integer status;
}
