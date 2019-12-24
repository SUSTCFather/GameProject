package com.example.gameproject.web;

import com.alibaba.fastjson.JSON;
import com.example.gameproject.Constant;
import com.example.gameproject.bean.model.GameRecord;
import com.example.gameproject.bean.request.FriendRequest;
import com.example.gameproject.bean.request.UserRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.service.MailService;
import com.example.gameproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public HttpResult register(@RequestBody UserRequest request) {
        HttpResult result = new HttpResult();
        boolean verifyResult = mailService.verifyCheckCode(request);
        if(verifyResult) {
            return userService.register(request);
        }else {
            result.setStatus(Constant.FAIL);
            result.setMessage("验证码错误");
            return result;
        }
    }

    @PostMapping("/login")
    public HttpResult logIn(@RequestBody UserRequest request) {
        System.out.println(JSON.toJSONString(request));
        return userService.login(request);
    }

    @PostMapping("/sendCheckCode")
    public HttpResult sendCheckCode(@RequestBody UserRequest request) {
        User userFound = userService.findUser(request.getUserName(),request.getMailAddress());
        if(userFound != null) {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("该用户名或邮箱已被注册");
            return httpResult;
        }
        return mailService.sendCheckCode(request.getMailAddress());
    }

    @PostMapping("/verifyChange")
    public HttpResult verifyChangeCheckCode(@RequestBody UserRequest request) {
        User userFound = userService.findUserByMail(request.getMailAddress());
        if(userFound == null) {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("该邮箱未被注册");
            return httpResult;
        }
        return mailService.sendCheckCode(request.getMailAddress());
    }

    @PostMapping("/changePassword")
    public HttpResult changePassword(@RequestBody UserRequest request) {
        HttpResult result = new HttpResult();
        boolean verifyResult = mailService.verifyCheckCode(request);
        if(verifyResult) {
            return userService.changePassword(request);
        }else {
            result.setStatus(Constant.FAIL);
            result.setMessage("验证码错误");
            return result;
        }
    }

    @GetMapping("/personal")
    public HttpResult getPersonInfo(long userId) {
        return userService.getPersonInfo(userId);
    }

    @GetMapping("/history")
    public HttpResult getHistory(long userId) {
        return userService.getHistory(userId);
    }

    @GetMapping("/rankList")
    public HttpResult getRankList() {
        return userService.getRankList();
    }

}
