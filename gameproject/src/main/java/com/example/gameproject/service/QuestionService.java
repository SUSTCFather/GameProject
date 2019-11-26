package com.example.gameproject.service;

import com.example.gameproject.Constant;
import com.example.gameproject.api.AnswerRepository;
import com.example.gameproject.api.QuestionRepository;
import com.example.gameproject.api.UserRepository;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.request.QuestionRequest;
import com.example.gameproject.bean.response.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
            httpResult.setStatus(Constant.SUCCESS);
            httpResult.setData(question);
        }else {
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("找不到题目，id: "+request.getQuestionId());
        }
        return httpResult;
    }

}
