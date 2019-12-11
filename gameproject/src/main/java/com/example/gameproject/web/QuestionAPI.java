package com.example.gameproject.web;

import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.request.QuestionRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.service.QuestionService;
import com.example.gameproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionAPI {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/get")
    public HttpResult getQuestion() {
        return questionService.getQuestion();
    }
}
