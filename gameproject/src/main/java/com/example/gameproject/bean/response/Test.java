package com.example.gameproject.bean.response;

import com.alibaba.fastjson.JSON;
import com.example.gameproject.api.UserRepository;
import com.example.gameproject.socket.SocketData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        Test test = new Test();
        test.userRepository.findAll();


    }
}
