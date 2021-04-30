package com.woniu.service;


import com.woniu.domain.OilRecord;
import com.woniu.domain.ReturnRecord;

import java.util.List;

public interface OilRecordService {

    public List<OilRecord> oilRecordList();

    int add(OilRecord oilRecord);

    int update(OilRecord oilRecord);

    int delete(Integer id);

}
