package com.example.gameproject.service;

import com.example.gameproject.api.AnswerRepository;
import com.example.gameproject.api.MailRepository;
import com.example.gameproject.api.QuestionRepository;
import com.example.gameproject.api.UserRepository;
import com.example.gameproject.bean.model.Answer;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.model.Relationship;
import com.example.gameproject.bean.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> findUserById(Long id) {
        User user = userRepository.findByUserId(id);
        List<User> userList = new ArrayList<>();
        List<Relationship> followers = user.getFollowers(); //fromUser
        for(Relationship relationship : followers) {
            userList.add(relationship.getFromUser());
        }
        List<Relationship> followUsers = user.getFollowUsers(); //toUser
        for(Relationship relationship : followUsers) {
            userList.add(relationship.getToUser());
        }
        return userList;
    }


    public Question getQuestion() {
        return questionRepository.findRand();
    }

}
