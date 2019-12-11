package com.example.gameproject.web;

import com.alibaba.fastjson.JSON;
import com.example.gameproject.Constant;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.bean.request.EnterRequest;
import com.example.gameproject.bean.request.InviteRequest;
import com.example.gameproject.bean.request.UserRequest;
import com.example.gameproject.bean.response.GameData;
import com.example.gameproject.bean.response.Hall;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.service.UserService;
import com.example.gameproject.socket.SocketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameAPI {

    @Autowired
    private UserService userService;

    @GetMapping("/getHallList")
    public HttpResult getHall() {
        HttpResult<List<Hall>> result = new HttpResult<>();
        result.setStatus(Constant.SUCCESS);
        result.setMessage("Hall");
        result.setData(SocketData.hallList);
        return result;
    }

    @GetMapping("/getHall")
    public HttpResult getSingleHall(int hallId) {
        HttpResult<List<Hall>> result = new HttpResult<>();
        List<Hall> halls = new ArrayList<>();
        int length = SocketData.hallList.size();
        if(hallId > length || hallId < 1) {
            result.setStatus(Constant.FAIL);
            result.setMessage("错误房间号");
        }else {
            result.setStatus(Constant.SUCCESS);
            halls.add(SocketData.hallList.get(hallId - 1));
            result.setData(halls);
        }
        return result;
    }

    @PostMapping("/enter")
    public HttpResult enter(@RequestBody EnterRequest request) {
        SocketData.enter(request);
        HttpResult<EnterRequest> result = new HttpResult<>();
        System.out.printf("%s加入%d号房间\n",request.getPlayer().getUserName(),request.getHallId());
        result.setMessage("进入房间成功");
        result.setStatus(Constant.SUCCESS);
        result.setData(request);
        return result;
    }

    @PostMapping("/exit")
    public HttpResult exit(@RequestBody EnterRequest request) {
        SocketData.exit(request);
        HttpResult result = new HttpResult<>();
        System.out.printf("%s退出%d号房间\n",request.getPlayer().getUserName(),request.getHallId());
        result.setMessage("退出房间成功");
        result.setStatus(Constant.SUCCESS);
        return result;
    }

    @PostMapping("/ready")
    public HttpResult ready(@RequestBody EnterRequest request) throws IOException {
        SocketData.ready(request);
        HttpResult result = new HttpResult<>();
        System.out.printf("%s做好准备了\n",request.getPlayer().getUserName());
        result.setStatus(Constant.SUCCESS);
        return result;
    }


}
