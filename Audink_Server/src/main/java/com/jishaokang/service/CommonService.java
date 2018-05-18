package com.jishaokang.service;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.User;

import java.io.IOException;


/**
 * Created by NANA_Final on 2017/7/21.
 */

public interface CommonService {


    Result getHomepage(User user) throws IOException;
}