package com.huism.community.dto;

import com.huism.community.exception.CustomizeErrorCode;
import com.huism.community.exception.CustomizeException;
import lombok.Data;

@Data
public class CommentResponseDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static CommentResponseDTO errorOf(Integer code, String message){
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        return responseDTO;
    }

    public static CommentResponseDTO errorOf(CustomizeErrorCode noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMessage());
    }

    public static CommentResponseDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }



    public static CommentResponseDTO okOf(){
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("请求成功。。。");
        return responseDTO;
    }

    public static <T> CommentResponseDTO okOf(T t){
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("请求成功。。。");
        responseDTO.setData(t);
        return responseDTO;
    }
}
