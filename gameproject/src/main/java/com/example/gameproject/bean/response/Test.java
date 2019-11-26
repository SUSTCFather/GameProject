package com.example.gameproject.bean.response;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        GameData gameData = new GameData();
        String s = JSON.toJSONString(gameData);
        GameData g = JSON.parseObject(s,GameData.class);
        System.out.println();

    }
}
