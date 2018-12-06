package com.zzk.ssmdemo.utils;

import com.zzk.ssmdemo.entity.Result;
import com.zzk.ssmdemo.enums.ResultEnum;

/**
 * @author situliang
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(ResultEnum.OPERATION_SUCCESS.getCode());
        result.setMsg(ResultEnum.OPERATION_SUCCESS.getMsg());
        result.setData(object);

        return result;
    }


    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }


}
