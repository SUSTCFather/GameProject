package com.example.gameproject.service;

import com.example.gameproject.HttpUtil;
import com.example.gameproject.api.AnswerRepository;
import com.example.gameproject.api.QuestionRepository;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.request.QuestionRequest;
import com.example.gameproject.bean.response.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public HttpResult getQuestion(QuestionRequest request) {
        HttpResult<Question> httpResult = new HttpResult<>();
        Question question = questionRepository.findByQuestionId(request.getQuestionId());
        if(question != null) {
            httpResult.setStatus(HttpUtil.SUCCESS);
            httpResult.setData(question);
        }else {
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("找不到题目，id: "+request.getQuestionId());
        }
        return httpResult;
    }

}
