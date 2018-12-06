package com.zzk.ssmdemo.handle;

import com.zzk.ssmdemo.entity.Result;
import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.UserException;
import com.zzk.ssmdemo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author situliang
 */
@ControllerAdvice
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof UserException) {
            UserException userException = (UserException) e;
            return ResultUtil.error(userException.getCode(), userException.getMessage());
        } else {
            logger.error("系统异常:{}", e);
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),
                    ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
