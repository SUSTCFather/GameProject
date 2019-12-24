package com.example.gameproject.service;

import com.example.gameproject.Constant;
import com.example.gameproject.mapper.QuestionRepository;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.response.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public HttpResult getQuestion() {
        HttpResult<Question> httpResult = new HttpResult<>();
        Question question = questionRepository.findRand();
        httpResult.setStatus(Constant.SUCCESS);
        httpResult.setData(question);
        return httpResult;
    }

}
