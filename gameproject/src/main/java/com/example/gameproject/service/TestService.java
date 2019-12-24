package com.example.gameproject.service;

import com.example.gameproject.mapper.AnswerRepository;
import com.example.gameproject.mapper.GameRecordRepository;
import com.example.gameproject.mapper.QuestionRepository;
import com.example.gameproject.mapper.UserRepository;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.model.GameRecord;
import com.example.gameproject.bean.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRecordRepository gameRecordRepository;

    public void testAddGameRecord() {
        GameRecord gameRecord = new GameRecord();
        gameRecord.setWinner(new User(1));
        gameRecord.setLoser(new User(2));
        gameRecord.setGameTime(new Date());
        gameRecordRepository.save(gameRecord);
    }

    public List<User> testGetAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "score");
        PageRequest pageRequest = PageRequest.of(0,15,sort);
        return userRepository.findAllBy(pageRequest);
    }

}
