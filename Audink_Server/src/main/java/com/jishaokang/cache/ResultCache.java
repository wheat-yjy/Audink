package com.jishaokang.cache;


import com.jishaokang.base.Result;

import static com.jishaokang.base.ResultStatusMessage.M_FAILURE;
import static com.jishaokang.base.ResultStatusMessage.M_OK;
import static com.jishaokang.base.ResultStatusMessage.M_PERMISSION_DENIED;
import static com.jishaokang.base.ResultStatusValues.V_FAILURE;
import static com.jishaokang.base.ResultStatusValues.V_OK;
import static com.jishaokang.base.ResultStatusValues.V_PERMISSION_DENIED;

public class ResultCache {
    public static final Result OK = new Result(V_OK,M_OK);
    public static final Result FAILURE = new Result(V_FAILURE,M_FAILURE);
    public static final Result PERMISSION_DENIED = new Result(V_PERMISSION_DENIED,M_PERMISSION_DENIED);

    public static Result getDataOk(Object object){
        Result result = new Result(V_OK,M_OK);
        result.setData(object);
        return result;
    }

    public static Result getDataFailure(String message){
        Result result = new Result(V_FAILURE,message);
        return result;
    }

}