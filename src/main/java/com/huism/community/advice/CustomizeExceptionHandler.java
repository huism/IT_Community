package com.huism.community.advice;

import com.alibaba.fastjson.JSON;
import com.huism.community.dto.CommentResponseDTO;
import com.huism.community.exception.CustomizeErrorCode;
import com.huism.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model,
                        HttpServletResponse response) {
        HttpStatus status = getStatus(request);

        String contentType = request.getContentType();

        if("application/json".equals(contentType)){
            //返回json
            CommentResponseDTO responseDTO;
            if(ex instanceof CustomizeException){
                responseDTO = CommentResponseDTO.errorOf((CustomizeException) ex);
            }else {
                responseDTO = CommentResponseDTO.errorOf(CustomizeErrorCode.SERVICE_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(responseDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }else {
            // 返回html
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message","服务崩了。。。");
            }

            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
