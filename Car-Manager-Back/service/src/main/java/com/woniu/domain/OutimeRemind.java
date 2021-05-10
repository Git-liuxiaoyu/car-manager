package com.woniu.domain;

import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/4/30 11:58
 */
@Data
public class OutimeRemind {
    private Integer id;
    private Integer carId;//车辆id
    private String carNum;//车牌号
    private Integer startcarId;//修改时的原车牌id

    private String outDate;
    private String startoutDate;//修改时的原到期时间

    private Integer type;//到期类别id
    private String typename;//到期类别名
    private Integer starttype;//修改时的原到期类别id
}
