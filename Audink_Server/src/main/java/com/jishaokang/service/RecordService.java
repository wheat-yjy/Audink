package com.jishaokang.service;



import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Record;

import javax.servlet.http.HttpSession;
public interface RecordService {


    Result getRecord(HttpSession session);

    Result addRecord(Record record, HttpSession session);

    Result deleteRecord(int recordId);

    Result finishRecord(int recordId);

}
