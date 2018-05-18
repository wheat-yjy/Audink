package com.jishaokang.service;

import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Collect;


/**
 * Created by NANA_Final on 2017/7/21.
 */

public interface CollectService {


    Result getCollect(Integer userId);

    Result addCollect(Collect collect);

    Result deleteCollect(Collect collect);
}