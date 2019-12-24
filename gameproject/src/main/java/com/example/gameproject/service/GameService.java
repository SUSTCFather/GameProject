package com.example.gameproject.service;

import com.example.gameproject.Constant;
import com.example.gameproject.mapper.GameRecordRepository;
import com.example.gameproject.mapper.UserRepository;
import com.example.gameproject.bean.model.GameRecord;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.bean.response.GameData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class GameService {

    @Autowired
    private GameRecordRepository gameRecordRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveGameRecord(GameData gameData) {
        GameRecord gameRecord = new GameRecord();
        long whiteId = Long.parseLong(gameData.getWhitePlayer().getUserId());
        long blackId = Long.parseLong(gameData.getBlackPlayer().getUserId());
        User whiteUser = userRepository.findByUserId(whiteId);
        User blackUser = userRepository.findByUserId(blackId);
        if(gameData.getWinner() == Constant.WHITE) {
            whiteUser.winGame();
            blackUser.lostGame();
            gameRecord.setWinner(whiteUser);
            gameRecord.setLoser(blackUser);
        }else {
            blackUser.winGame();
            whiteUser.lostGame();
            gameRecord.setWinner(blackUser);
            gameRecord.setLoser(whiteUser);
        }
        gameRecord.setGameTime(new Date());
        userRepository.save(whiteUser);
        userRepository.save(blackUser);
        gameRecordRepository.save(gameRecord);
    }


}
