package com.example.gameproject.web;

import com.example.gameproject.bean.model.Question;
import com.example.gameproject.service.QuestionService;
import com.example.gameproject.service.TestService;
import com.example.gameproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public String addQuestion() {
        return testService.addQuestion();
    }

    @PostMapping("/addAnswer")
    public String addAnswer(@RequestParam int questionID) {
        return testService.addAnswer(questionID);
    }

//    @PostMapping("/getQuestion")
//    public Page<Question> getQuestion(@RequestParam int questionID) {
//        return questionService.getQuestion(questionID);
//    }

}
