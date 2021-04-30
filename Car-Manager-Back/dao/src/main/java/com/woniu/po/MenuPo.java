package com.woniu.po;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/14:47
 * @Description:
 */
@Data
public class MenuPo {
   private Integer id;
    private Integer parentId;
    private String name;
    private String href;
    private String userName;
    private List<MenuPo> children;
}
