package com.jishaokang.service.impl;


import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.RecordDAO;
import com.jishaokang.model.dto.Record;
import com.jishaokang.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Service
public class RecordServiceImpl implements RecordService {


    @Autowired
    private RecordDAO recordDAO;

    @Override
    public Result getRecord(HttpSession session) {
        int userId =(Integer) session.getAttribute("userId");
        int total = recordDAO.countAll(userId);
        List<Record> records = recordDAO.select(userId);
        //Map data = new HashMap();
        //data.put("total",total);
        //data.put("records",records);
        return ResultCache.getDataOk(records);
    }

    @Override
    public Result addRecord(Record record, HttpSession session) {
        int userId =(Integer) session.getAttribute("userId");
        record.setUserId(userId);
        recordDAO.insert(record);
        return ResultCache.OK;
    }

    @Override
    public Result deleteRecord(int recordId) {
        recordDAO.delete(recordId);
        return ResultCache.OK;
    }

    @Override
    public Result finishRecord(int recordId) {
        recordDAO.updateFinish(recordId);
        return ResultCache.OK;
    }

}