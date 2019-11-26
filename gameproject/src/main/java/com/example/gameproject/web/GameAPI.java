package com.example.gameproject.web;

import com.example.gameproject.Constant;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.bean.request.InviteRequest;
import com.example.gameproject.bean.request.UserRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.service.UserService;
import com.example.gameproject.socket.SocketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;

@RestController
@RequestMapping("/game")
public class GameAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/invite")
    public HttpResult invite(@RequestBody InviteRequest request) {
        HttpResult result = new HttpResult();
        Session session = SocketData.getSession(request.getToUserId()+"");
        if(session == null) {
            result.setStatus(Constant.FAIL);
            result.setMessage("该用户不在线");
            return result;
        }
        result.setStatus(Constant.SUCCESS);
        result.setMessage("邀请成功");
        return result;
    }

}
