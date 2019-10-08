package com.huism.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "你找的问题不在了，看看其他问题吧。。。"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复。。。"),
    NO_LOGIN(2003, "未登录，不能进行评论。。。"),
    SERVICE_ERROR(5001,"服务崩了，请稍后。。。"),
    TYPE_PARAM_WRONG(2004,"评论类型错误。。。"),
    COMMENT_NOT_FOUND(2005,"评论没找到呀。。。"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
