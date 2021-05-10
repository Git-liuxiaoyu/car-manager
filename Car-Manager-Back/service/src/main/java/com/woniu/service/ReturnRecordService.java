package com.woniu.service;


import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;

import java.util.List;

public interface ReturnRecordService {



    void add(ReturnRecord returnRecord);


    ReturnRecord getByDictionaryId(Integer id);
}
