package com.huism.community.controller;

import com.huism.community.dto.QuestionDTO;
import com.huism.community.model.Question;
import com.huism.community.model.User;
import com.huism.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    // 首次进入发布问题界面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    // 发布问题提交请求
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model){
        // 会写表单数据
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || "".equals(tag)){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(description == null || "".equals(description)){
            model.addAttribute("error","问题补充不能为空！");
            return "publish";
        }
        if(tag == null || "".equals(tag)){
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

        //
        question.setId(id);

        // 插入问题数据到数据库
        questionService.createOrUpdate(question);

        // 插入问题成功，返回首页
        return "redirect:/";
    }

    // 编辑问题请求
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id, Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        return "/publish";
    }

}
