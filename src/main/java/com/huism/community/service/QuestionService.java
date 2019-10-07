package com.huism.community.service;

import com.huism.community.dto.PaginationDTO;
import com.huism.community.dto.QuestionDTO;
import com.huism.community.mapper.QuestionMapper;
import com.huism.community.mapper.UserMapper;
import com.huism.community.model.Question;
import com.huism.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    // 返回所有问题列表 , page表示第几页，size表示每页有多少个
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        // 总记录数
        Integer totalCount = questionMapper.count();
        // 总页数
        Integer totalPage = (totalCount%size == 0)?(totalCount/size):(totalCount/size+1);
        // 防止页数溢出
        if(page < 1){
            page=1;
        }else if(page > totalPage){
            page=totalPage;
        }

        // 设置分页
        Integer offset = size*(page-1);


        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOS);
        paginationDTO.setPagination(totalPage,page);

        return paginationDTO;
    }

    // listQuestionByUserId
    public PaginationDTO list(Integer id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        // 总记录数
        Integer totalCount = questionMapper.countByUserId(id);
        // 总页数
        Integer totalPage = (totalCount%size == 0)?(totalCount/size):(totalCount/size+1);
        // 防止页数溢出
        if(page < 1){
            page=1;
        }else if(page > totalPage) {
            page = totalPage;
        }

        // 设置分页
        Integer offset = size*(page-1);


        List<Question> questions = questionMapper.listByUserId(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOS);
        paginationDTO.setPagination(totalPage,page);

        return paginationDTO;
    }

    // 通过问题id查找问题
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreator());

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {

        if(question.getId() == null){
            // 创建question
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            // 编辑question
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
