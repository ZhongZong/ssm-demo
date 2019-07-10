package com.zzk.ssmdemo.enums;

public enum ResultEnum {

    /**
     *
     */
    UNKNOWN_ERROR(-1, "未知错误"),
    OPERATION_SUCCESS(0, "操作成功"),
    PARAMETER_ERROR(100, "参数错误"),
    PARSE_ERROR(101, "请求解析错误"),
    RESPONSE_ERROR(102, "返回消息失败");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
