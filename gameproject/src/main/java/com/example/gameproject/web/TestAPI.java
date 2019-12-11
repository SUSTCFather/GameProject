package com.example.gameproject.web;

import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.model.Relationship;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.service.QuestionService;
import com.example.gameproject.service.TestService;
import com.example.gameproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/getQuestion")
    public Question getQuestion() {
        return testService.getQuestion();
    }



}
