package com.example.gameproject.web;

import com.example.gameproject.HttpUtil;
import com.example.gameproject.bean.request.RegisterRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.bean.model.User;
import com.example.gameproject.service.MailService;
import com.example.gameproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public HttpResult register(@RequestBody RegisterRequest request) {
        HttpResult result = new HttpResult();
        boolean verifyResult = mailService.verifyCheckCode(request);
        if(verifyResult) {
            return userService.register(request);
        }else {
            result.setStatus(HttpUtil.FAIL);
            result.setMessage("验证码错误");
            return result;
        }
    }

    @PostMapping("/login")
    public HttpResult logIn(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/sendCheckCode")
    public HttpResult sendCheckCode(@RequestBody RegisterRequest request) {
        User userFound = userService.findUser(request.getUserName(),request.getMailAddress());
        if(userFound != null) {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("该用户名或邮箱已被注册");
            return httpResult;
        }
        return mailService.sendCheckCode(request.getMailAddress());
    }

    @PostMapping("/verifyChange")
    public HttpResult verifyChangeCheckCode(@RequestBody RegisterRequest request) {
        User userFound = userService.findUser(request.getMailAddress());
        if(userFound == null) {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("该邮箱未被注册");
            return httpResult;
        }
        return mailService.sendCheckCode(request.getMailAddress());
    }

    @PostMapping("/changePassword")
    public HttpResult changePassword(@RequestBody RegisterRequest request) {
        HttpResult result = new HttpResult();
        boolean verifyResult = mailService.verifyCheckCode(request);
        if(verifyResult) {
            return userService.changePassword(request);
        }else {
            result.setStatus(HttpUtil.FAIL);
            result.setMessage("验证码错误");
            return result;
        }
    }


}
