package com.example.gameproject.service;

import com.example.gameproject.api.AnswerRepository;
import com.example.gameproject.api.MailRepository;
import com.example.gameproject.api.QuestionRepository;
import com.example.gameproject.bean.model.Answer;
import com.example.gameproject.bean.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public String addQuestion() {
        Question question = new Question();
        question.setContent("题目1");
        questionRepository.save(question);
        return "ok";
    }

    public Page<Question> getQuestion(long id) {
        PageRequest pr = PageRequest.of(0, 10);
        return questionRepository.findAll(pr);
    }

    public String addAnswer(int questionID){
        Answer answer = new Answer();
        Question question = questionRepository.findByQuestionId(questionID);
        if(question != null) {
            answer.setContent("湖人");
            answer.setSymbol("A");
            answer.setType(1);
            answer.setQuestion(question);
            answerRepository.save(answer);
            return "ok";
        }

        return "shit";
    }

}
