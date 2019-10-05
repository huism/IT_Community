package com.huism.community.controller;

import com.huism.community.mapper.QuestionMapper;
import com.huism.community.model.Question;
import com.huism.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){
        // 会写表单数据
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title==""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(description == null || description==""){
            model.addAttribute("error","问题补充不能为空！");
            return "publish";
        }
        if(tag == null || tag==""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }

        // 将获取到的问题表单封装成Question对象
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        // 关联问题发布者
        User user = null;
        Object object = request.getSession().getAttribute("user");
        if(object instanceof User){
            user=(User)object;
            question.setCreator(user.getId());
            model.addAttribute("error","success");
        }else{
            model.addAttribute("error","用户未登录！");
            return "publish";
        }

        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate() );

        // 插入问题数据到数据库
        questionMapper.create(question);

        // 插入问题成功，返回首页
        return "redirect:/";
    }
}
