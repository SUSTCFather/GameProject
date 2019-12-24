package com.example.gameproject.web;

import com.alibaba.fastjson.JSON;
import com.example.gameproject.bean.model.GameRecord;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.service.QuestionService;
import com.example.gameproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/getAll")
    public List<User> getQuestion() {
        return testService.testGetAll();
    }

    @GetMapping("/addGame")
    public String addGame() {
        testService.testAddGameRecord();
        return "fuck";
    }



}
