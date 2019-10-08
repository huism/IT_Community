package com.huism.community.mapper;

import com.huism.community.model.Question;

public interface QuestionExtMapper {
    int increaseView(Question question);
    int increaseComment(Question question);
}