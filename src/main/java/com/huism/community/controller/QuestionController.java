package com.huism.community.controller;

import com.huism.community.dto.QuestionDTO;
import com.huism.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        // 增加阅读数
        questionService.increaseView(id);
        return "question";
    }
}
