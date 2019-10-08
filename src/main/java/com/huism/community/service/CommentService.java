package com.huism.community.service;

import com.huism.community.enums.CommentTypeEnum;
import com.huism.community.exception.CustomizeErrorCode;
import com.huism.community.exception.CustomizeException;
import com.huism.community.mapper.CommentMapper;
import com.huism.community.mapper.QuestionExtMapper;
import com.huism.community.mapper.QuestionMapper;
import com.huism.community.model.Comment;
import com.huism.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    // 插入评论
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.increaseComment(question);
        }
    }
}
