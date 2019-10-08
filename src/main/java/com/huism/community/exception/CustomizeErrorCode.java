package com.huism.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUSESTION_NOT_FOUND("你找的问题不在了，看看其他问题吧。。。");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
