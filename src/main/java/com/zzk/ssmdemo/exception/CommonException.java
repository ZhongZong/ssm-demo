package com.zzk.ssmdemo.exception;

import com.zzk.ssmdemo.enums.ResultEnum;

/**
 * @ClassName CommonException
 * @Description: 基本的异常类
 * @Author situliang
 * @Date 2019/7/13
 * @Version V1.0
 **/

public class CommonException extends RuntimeException {

    private Integer code;

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
