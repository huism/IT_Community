package com.huism.community.controller;

import com.huism.community.dto.CommentCreateDTO;
import com.huism.community.dto.CommentDTO;
import com.huism.community.dto.CommentResponseDTO;
import com.huism.community.enums.CommentTypeEnum;
import com.huism.community.exception.CustomizeErrorCode;
import com.huism.community.model.Comment;
import com.huism.community.model.User;
import com.huism.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 回复评论
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        // 用户未登录异常处理
        if(user == null){
            return CommentResponseDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        // 回复为空异常处理
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return CommentResponseDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        // 回复的问题不存在
        if(commentCreateDTO.getParentId() == null || commentCreateDTO.getParentId() <= 0){
            return CommentResponseDTO.errorOf(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return CommentResponseDTO.okOf();
    }


    // 获取二级评论
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public CommentResponseDTO<List<CommentDTO>> comment(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return CommentResponseDTO.okOf(commentDTOS);
    }
}
