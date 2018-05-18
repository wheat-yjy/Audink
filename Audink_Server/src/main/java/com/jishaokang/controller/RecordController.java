package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@RestController
@RequestMapping(value = "/api/user")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "/getRecord",method = RequestMethod.POST)
    public Result getRecords(HttpSession session){
        return recordService.getRecord(session);
    }

    @RequestMapping(value = "/deleteRecord",method = RequestMethod.POST)
    public Result deleteRecord(int recordId){
        return recordService.deleteRecord(recordId);
    }


}